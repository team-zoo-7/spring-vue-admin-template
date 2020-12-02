package com.ifsaid.shark.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 그래픽 검증 코드 생성 도구
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/14 14:43
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public class VerifyCodeUtils {

    private final static VerifyCode VERIFY_CODE = new VerifyCode(10);

    /**
     * 인증 코드 받기
     *
     * @return BufferedImage
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 14:58
     */
    public static ImageVerifyCode getImage() {
        BufferedImage image = VERIFY_CODE.getImage();
        String codeText = VERIFY_CODE.getText();
        return new ImageVerifyCode(codeText, image);
    }

    @Getter
    @AllArgsConstructor
    public static class ImageVerifyCode {

        private String codeText;

        private BufferedImage image;

    }

    private static class VerifyCode {
        private final static int WIDTH = 120;

        private final static int HEIGHT = 50;

        private final static int CODE_LENGTH = 4;

        private final static Random RANDOM = new Random();

        /**
         * 확인 코드 글꼴
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/14 14:48
         */
        private final static String[] FONT_NAMES = {"宋体", "微软雅黑"};

        /**
         * 선택적 문자
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/14 14:49
         */
        private final static String CODES = "13456789abcdefghjkmnpqrstuvwxyABCDEFGHJKMNPQRSTUVWXY";

        /**
         * 배경색
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/14 14:49
         */
        private Color bgColor = new Color(255, 255, 255);

        /**
         * 인증 코드의 텍스트
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @return
         * @exception
         * @date 2019/12/14 14:49
         */
        private String text;

        public VerifyCode(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        public VerifyCode(int minute) {
            // 몇 분 후
            this.localDateTime = LocalDateTime.now().plusMinutes(minute);
        }

        //만료 시각
        private LocalDateTime localDateTime;

        /**
         * 임의의 색상 생성
         *
         * @return Color
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/14 14:48
         */
        private Color randomColor() {
            int red = RANDOM.nextInt(150);
            int green = RANDOM.nextInt(150);
            int blue = RANDOM.nextInt(150);
            return new Color(red, green, blue);
        }

        /**
         * 임의의 글꼴 생성
         *
         * @return Font
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/14 14:47
         */
        private Font randomFont() {
            int index = RANDOM.nextInt(FONT_NAMES.length);
            //임의의 글꼴 이름 생성
            String fontName = FONT_NAMES[index];
            //임의의 스타일 생성, 0 (스타일 없음), 1 (굵게), 2 (기울임 꼴), 3 (굵게 + 기울임 꼴)
            int style = RANDOM.nextInt(4);
            //임의의 글꼴 크기 생성, 26 ~ 30
            int size = RANDOM.nextInt(5) + 26;
            return new Font(fontName, style, size);
        }

        /**
         * 간섭 선 그리기
         *
         * @param image
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/14 14:47
         */
        private void drawLine(BufferedImage image) {
            //총 3 장 뽑기
            int num = 3;
            Graphics2D g2 = (Graphics2D) image.getGraphics();
            //두 점의 좌표, 즉 4 개 값 생성
            for (int i = 0; i < num; i++) {
                int x1 = RANDOM.nextInt(WIDTH);
                int y1 = RANDOM.nextInt(HEIGHT);
                int x2 = RANDOM.nextInt(WIDTH);
                int y2 = RANDOM.nextInt(HEIGHT);
                g2.setStroke(new BasicStroke(1.5F));
                //간섭 선이 파란색입니다.
                g2.setColor(Color.BLUE);
                //선을 그리다
                g2.drawLine(x1, y1, x2, y2);
            }
        }

        /**
         * 무작위로 캐릭터 생성
         *
         * @return char
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/14 14:47
         */
        private char randomChar() {
            int index = RANDOM.nextInt(CODES.length());
            return CODES.charAt(index);
        }

        /**
         * BufferedImage 만들기
         *
         * @return java.awt.image.BufferedImage
         * @throws
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/14 14:46
         */
        private BufferedImage createImage() {
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = (Graphics2D) image.getGraphics();
            g2.setColor(this.bgColor);
            g2.fillRect(0, 0, WIDTH, HEIGHT);
            return image;
        }

        /**
         * 이 메서드를 호출하여 인증 코드를받습니다.
         *
         * @return java.awt.image.BufferedImage
         * @throws
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/14 14:46
         */
        public BufferedImage getImage() {
            //그림 버퍼 만들기
            BufferedImage image = createImage();
            //그리기 환경 얻기
            Graphics2D g2 = (Graphics2D) image.getGraphics();
            //생성 된 인증 코드 텍스트를로드하는 데 사용됩니다.
            StringBuilder sb = new StringBuilder();
            // 그림에 4 자 그리기
            //한 문자를 생성 할 때마다 4 번 반복
            for (int i = 0; i < CODE_LENGTH; i++) {
                //무작위로 편지 생성
                String s = randomChar() + "";
                //추가 문자를 sb에 넣으십시오.
                sb.append(s);
                //현재 캐릭터의 x 축 좌표 설정
                float x = i * 1.0F * WIDTH / 4;
                //임의의 글꼴 설정
                g2.setFont(randomFont());
                //임의의 색상 설정
                g2.setColor(randomColor());
                //그림
                g2.drawString(s, x, HEIGHT - 5);
            }
            //생성 된 문자열을 this.text에 할당합니다.
            this.text = sb.toString();
            //간섭 선 추가
            drawLine(image);
            return image;
        }

        /**
         * 인증 코드 이미지에 텍스트 반환
         *
         * @return String
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/14 14:44
         */
        public String getText() {
            return text;
        }

        /**
         * 지정된 출력 스트림에 사진 저장
         *
         * @param image
         * @param out
         * @return void
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/14 14:44
         */
        public static void output(BufferedImage image, OutputStream out)
                throws IOException {
            ImageIO.write(image, "JPEG", out);
        }

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        public boolean isExpired() {
            return LocalDateTime.now().isAfter(localDateTime);
        }


    }

}
