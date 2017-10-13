package net.ujacha.onmyojibot.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
			if (StringUtils.equals(s.getName(), name)) {
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

	
}
