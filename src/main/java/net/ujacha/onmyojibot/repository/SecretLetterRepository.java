package net.ujacha.onmyojibot.repository;

import net.ujacha.onmyojibot.entity.SecretLetter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecretLetterRepository {

    private List<SecretLetter> secretLetters = new ArrayList<>();

    public void save(SecretLetter sl){
        secretLetters.add(sl);
    }

    public List<SecretLetter> findByKeyword(String keyword){
        List<SecretLetter> list = secretLetters.stream().filter(sl -> {
            boolean b = false;
            if(StringUtils.contains(sl.getQuestion(), keyword)){
                b = true;
            }
            return b;
        }).collect(Collectors.toList());
        return list;
    }

}
