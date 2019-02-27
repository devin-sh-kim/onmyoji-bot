package net.ujacha.onmyojibot;

import net.ujacha.onmyojibot.entity.Location;
import net.ujacha.onmyojibot.entity.Shikigami;
import net.ujacha.onmyojibot.repository.ShikigamiRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchStageTest {

    @Autowired
    ShikigamiRepository shikigamiRepository;

    @Test
    public void test() {

        String keyword = "고획조";
        List<Shikigami> list = shikigamiRepository.findByStage(keyword);



        List<Location> locationList = new ArrayList<>();
        list.forEach(s -> {
            List<Location> locations = Stream.of(s.getLocations()).filter(l -> l.getValue().startsWith(keyword)).collect(Collectors.toList());

            locations.forEach(l -> {
                Location location = new Location();
                location.setValue(l.getValue());
                location.setCount(l.getCount());
                location.setType(s.getName());
                locationList.add(location);

            });


        });

        locationList.stream().sorted(Comparator.comparing(Location::getValue)).forEach(l -> {
            System.out.println(String.format("%s\t%s\t%d", l.getValue(), l.getType(), l.getCount()));
        });

    }

}
