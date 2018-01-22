package net.ujacha.onmyojibot.kakao;

import net.ujacha.onmyojibot.entity.Location;
import net.ujacha.onmyojibot.entity.SecretLetter;
import net.ujacha.onmyojibot.entity.Shikigami;
import net.ujacha.onmyojibot.repository.SecretLetterRepository;
import net.ujacha.onmyojibot.repository.ShikigamiRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OnmyojiBotService {

    public static final String START_TEXT = "시작";

    private static final String WELCOME_MESSAGE =
                    "찾는 식신의 이름을 입력하세요.\n" +
                    ">  황도깨비\n" +
                    "\n" +
                    "초성으로 찾아볼까요?\n" +
                    ">  ㅎㄷㄲㅂ\n" +
                    "\n" +
                    "신비 요괴 힌트를 입력하세요.\n" +
                    "(힌트는 하나씩만 입력해 주세요/힌트 초성은 아직...)\n" +
                    ">  허수아비\n" +
                    ">  명계\n" +
                    "\n" +
                    "봉마의 밀서 답이 궁금하다구요???\n" +
                    "문제에 나오는 단어로 검색해보세요!!\n" +
                    ">  " +
                    "\n" +
                    "즐거운 게임생활 되세요!";


    @Autowired
    private ShikigamiRepository shikigamiRepository;

    @Autowired
    private SecretLetterRepository secretLetterRepository;

    public Keyboard buildKeyboardByShikigamis(List<Shikigami> shikigamis) {

        Keyboard keyboard = new Keyboard();
        keyboard.setType("text");

        if (shikigamis != null) {
            if (shikigamis.size() > 1) {
                keyboard.setType("buttons");

                List<String> buttons = shikigamis.stream().map(s -> s.getName()).collect(Collectors.toList());
                buttons.add("다시검색");

                keyboard.setButtons(buttons.toArray(new String[]{}));
            }
        }

        return keyboard;
    }

    public Message buildFindInfo() {

        Message message = new Message();

        message.setText(WELCOME_MESSAGE);

        return message;
    }

    public Message buildMessage(List<Shikigami> shikigamis, List<SecretLetter> secretLetters) {
        Message message = new Message();

        String text = "";

        if (shikigamis != null) {

            // if (StringUtils.isNotEmpty(shikigami.getInfoPageUrl())) {
            // MessageButton messageButton = new MessageButton();
            // messageButton.setLabel("정보 보기");
            // messageButton.setUrl(shikigami.getInfoPageUrl());
            // message.setMessageButton(messageButton);
            // }

            if (shikigamis.size() > 1) {

                text = buildSelectShikigamiMassage(shikigamis);


            } else if (shikigamis.size() == 1) {

                Shikigami shikigami = shikigamis.get(0);

                text = buildShikigamiMessageText(shikigami);

                if (StringUtils.isNotEmpty(shikigami.getImageUrl())) {
                    Photo photo = new Photo();
                    photo.setUrl(shikigami.getImageUrl());
                    photo.setHeight(150);
                    photo.setWidth(150);
                    message.setPhoto(photo);
                }

            }

        }

        if(secretLetters != null && secretLetters.size() > 0){
            if(StringUtils.isEmpty(text)){
                text = buildSecretLetterMessage(secretLetters);
            }else{
                text = text + "\n\n========================\n\n" + buildSecretLetterMessage(secretLetters);
            }
        }

        if(StringUtils.isEmpty(text)) {
            // 없음
            text = "찾는 식신이 없습니다.\n다시 한번 확인해주세요.\n힌트는 하나만 적어주세요.";
        }
        message.setText(text);


        return message;
    }

    public String buildSelectShikigamiMassage(List<Shikigami> shikigamis) {

        StringBuffer sb = new StringBuffer();

        sb.append("다음 식신을 찾았습니다.\n\n");

        shikigamis.forEach(s -> {
            sb.append("- ").append(s.getName()).append("\n");
        });

        sb.append("\n찾고있는 식신을 선택하세요.");

        return sb.toString();
    }

    public String buildSecretLetterMessage(List<SecretLetter> secretLetters){
        StringBuffer sb = new StringBuffer();

        sb.append("봉마의 밀서를 찾았나요??\n\n");

        secretLetters.forEach(s -> {
            sb.append("Q. ").append(s.getQuestion()).append("\n")
                    .append("A. ").append(s.getAnswer()).append("\n\n");
        });

        return sb.toString();
    }

    public List<Shikigami> findShikigamis(String content) {

        String text = content.trim();

        // 힌트 검색
        List<Shikigami> find = shikigamiRepository.findByHint(text);

        // 이름 검색
        find.addAll(shikigamiRepository.findByName(text));

        // 초성 검색
        find.addAll(shikigamiRepository.findByInitialName(text));

        // 중복제거
        HashSet<Shikigami> hashSet = new HashSet<>(find);

        find = new ArrayList<>(hashSet);

        if (find != null && find.size() > 0) {
            return find;
        }
        return null;
    }

    public List<SecretLetter> findSecretLetters(String content){

        String text = content.trim();

        List<SecretLetter> find = secretLetterRepository.findByKeyword(text);

        if (find != null && find.size() > 0) {
            return find;
        }
        return null;
    }

    public String buildShikigamiMessageText(Shikigami shikigami) {
        StringBuffer sb = new StringBuffer();

        Location[] locations = shikigami.getLocations();

        int max = 0;
        Location recommend = null;
        for (Location l : locations) {

            if (StringUtils.equals("탐험", l.getType()) && StringUtils.isNumeric(l.getValue())
                    && Integer.parseInt(l.getValue()) > 99) {
                continue;
            }

            if (l.getCount() > max) {
                max = l.getCount();
                recommend = l;
            }

        }

        sb.append("찾은 식신: [").append(shikigami.getName()).append("]\n\n");
        if (recommend != null) {
            sb.append("추천 위치:\n");
            sb.append(buildLocation(recommend)).append("\n");
        }


        boolean apply = false;
        StringBuilder locationContent = new StringBuilder();
        for (Location l : locations) {
            if(l.getCount() > 0) {
                apply = true;
                locationContent.append(buildLocation(l));
            }
        }

        if(apply) {
            sb.append("출현 위치:\n").append(locationContent.toString());
        }else {
            sb.append("어디서 많이 본거 같은데....");
        }

        return sb.toString().trim();
    }

    public String buildLocation(Location l) {
        StringBuffer sb = new StringBuffer();

        sb.append("- ");

        sb.append(l.getType()).append(" ");
        if (StringUtils.equals("탐험", l.getType())) {
            sb.append("챕터-").append(l.getValue());
        } else if (StringUtils.equals("어혼던전", l.getType())) {
            sb.append(l.getValue()).append("층");
        } else if (StringUtils.equals("요기봉인", l.getType())) {
            sb.append(l.getValue());
        } else if (StringUtils.equals("비밀던전", l.getType())) {
            sb.append(l.getValue());
        }

        sb.append(" (").append(l.getCount()).append("마리)").append("\n");

        return sb.toString();
    }

}
