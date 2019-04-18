package net.ujacha.onmyojibot.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import net.ujacha.onmyojibot.entity.Shikigami;

@Component
public class ShikigamiRepository {

	private List<Shikigami> shikigamis = new ArrayList<>();

	public void save(Shikigami s) {

		shikigamis.add(s);

	}

	public List<Shikigami> findByHint(String hint) {

		List<Shikigami> list = shikigamis.stream().filter(s -> {
			boolean b = false;
			if (ArrayUtils.contains(s.getHints(), hint)) {
				b = true;
			}
			return b;
		}).collect(Collectors.<Shikigami>toList());

		return list;
	}

	public List<Shikigami> findByName(String name) {
		List<Shikigami> list = shikigamis.stream().filter(s -> {
			boolean b = false;
			if (StringUtils.startsWith(s.getName(), name)) {
				b = true;
			}
			return b;
		}).collect(Collectors.<Shikigami>toList());

		return list;
	}

	public List<Shikigami> findByInitialName(String initialName) {
		List<Shikigami> list = shikigamis.stream().filter(s -> {
			boolean b = false;
			if (StringUtils.equals(s.getInitialName(), initialName)) {
				b = true;
			}
			return b;
		}).collect(Collectors.<Shikigami>toList());

		return list;
	}


	public List<Shikigami> findByStage(String keyword) {

		List<Shikigami> list = shikigamis.stream().filter(s -> Stream.of(s.getLocations()).filter(l -> {
			if(StringUtils.equals(l.getType(), "비밀던전")){
				if (l.getValue().startsWith(keyword)){
					return true;
				}
			}
			return false;
		}).count() == 0 ? false : true).collect(Collectors.toList());

		return list;

	}
}
