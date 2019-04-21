package net.ujacha.onmyojibot;

import lombok.extern.slf4j.Slf4j;
import net.ujacha.onmyojibot.entity.Location;
import net.ujacha.onmyojibot.entity.Shikigami;
import net.ujacha.onmyojibot.model.*;
import net.ujacha.onmyojibot.repository.ShikigamiRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DataLoadTest {

    @Autowired
    private ShikigamiRepository shikigamiRepository;
    private List<Shikigami>  shikigamiList = new ArrayList<>();

    @Before
    public void setup(){
        this.shikigamiList = shikigamiRepository.getAllShikigami();
    }

    @Test
    public void test(){
        List<LocationShikigami> mapping = new ArrayList<>();

        // collect shikigami
        List<ShikigamiData> shikigamiDataList = shikigamiList.stream().filter(s -> !"식신".equals(s.getName()))
                .sorted((o1, o2) -> o1.getRarity().compareTo(o2.getRarity()) == 0 ? o1.getName().compareTo(o2.getName()) : o1.getRarity().compareTo(o2.getRarity()))
                .map(s -> {
            try {
                ShikigamiData d = ShikigamiData.builder()
                        .shikigamiId(s.getId())
                        .title(s.getName())
                        .rarity(ShikigamiRarity.valueOf(s.getRarity()))
                        .build();

                return d;
            }catch (Exception e){
                log.error("{}", e.getMessage());
                log.error("{}", s);
                return null;
            }
        }).collect(Collectors.toList());

        shikigamiDataList.forEach(d -> log.info("{}", d));
        log.info("{}", shikigamiDataList.size());



        // collect location
        List<LocationTable> locationTables = new ArrayList<>();
        AtomicLong locationTableId = new AtomicLong(0L);
        shikigamiList.stream().forEach(s -> {

            Location[] locations = s.getLocations();
            Stream.of(locations)
                    .filter(l ->
                        l.getType() != null
                        && !"".equals(l.getType())
                        && !"출연1".equals(l.getType())
                        && !"메시지".equals(l.getType())

                    )
                    .forEach(l -> {

                LocationTable t = LocationTable.builder()
                        .type(LocationType.valueOf(l.getType()))
                        .title(l.getValue())
                        .build();
                if(!locationTables.contains(t)){
                    t.setLocationId(locationTableId.incrementAndGet());
                    locationTables.add(t);
                }

                // mapping
                log.info("s: {}, l: {}-{}, count: {}", s.getName(), l.getType(), l.getValue(), l.getCount());
                LocationShikigami locationShikigami = LocationShikigami
                        .builder()
                        .locationId(locationTables.get(locationTables.indexOf(t)).getLocationId())
                        .shikigamiId(shikigamiDataList.get(
                                        shikigamiDataList.indexOf(
                                                ShikigamiData.builder()
                                                        .rarity(ShikigamiRarity.valueOf(s.getRarity()))
                                                        .title(s.getName())
                                                        .build())).getShikigamiId())
                        .count(l.getCount())
                        .build();

                        mapping.add(locationShikigami);

            });

        });

        List<LocationTable> sortedLocationTables = locationTables
                .stream()
                .sorted((o1, o2) -> o1.getType().compareTo(o2.getType()) == 0 ? o1.getTitle().compareTo(o2.getTitle()) : o1.getType().compareTo(o2.getType()))
                .collect(Collectors.toList());

        sortedLocationTables.forEach(l -> log.info("{}", l));


        mapping.stream()
                .sorted((o1, o2) -> (int) (o1.getLocationId() - o2.getLocationId()))
                .forEach(m -> {
                    LocationTable location = locationTables.stream().filter(l -> l.getLocationId() == m.getLocationId()).findAny().orElse(null);
                    ShikigamiData shikigami = shikigamiDataList.stream().filter(s -> s.getShikigamiId() == m.getShikigamiId()).findAny().orElse(null);

                    log.info("{}, {}, {}, {}", location.getType(), location.getTitle(), shikigami.getTitle(), m.getCount());

                });



        // TODO message

    }

}
