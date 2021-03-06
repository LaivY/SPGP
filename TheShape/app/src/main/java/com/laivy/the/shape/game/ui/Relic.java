package com.laivy.the.shape.game.ui;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;

public class Relic extends GameObject {
    // 유물 총 개수, 각 유물 id 설정
    public static int RELIC_COUNT               = 19;
    public static final int KETTLE_BELL         = 0;
    public static final int BLOOD_VIAL          = 1;
    public static final int BRONZE_SCALES       = 2;
    public static final int RED_SKULL           = 3;
    public static final int WAFFLE              = 4;
    public static final int SMOOTH_STONE        = 5;
    public static final int SOZU                = 6;
    public static final int GAMBLING_CHIP       = 7;
    public static final int WING_BOOTS          = 8;
    public static final int BRIMSTONE           = 9;
    public static final int MEAT                = 10;
    public static final int NINJA_SCROLL        = 11;
    public static final int CHAMPION_BELT       = 12;
    public static final int TINY_HOUSE          = 13;
    public static final int BAG_OF_PREP         = 14;
    public static final int ASTROLABE           = 15;
    public static final int BOTTLED_FLAME       = 16;
    public static final int BOTTLED_LIGHTNING   = 17;
    public static final int NUCLEAR_BATTERY     = 18;

    private final int id;
    private final String name;
    private final String desc;
    private boolean isActive;

    public Relic(int id) {
        this.id = id;
        switch (id) {
            case KETTLE_BELL:
                setBitmap(R.mipmap.kettlebell);
                name = "케틀벨";
                desc = "공격력 +2";
                break;
            case BLOOD_VIAL:
                setBitmap(R.mipmap.blood_vial);
                name = "피가 담긴 병";
                desc = "적 처치 시 체력 1 회복";
                break;
            case BRONZE_SCALES:
                setBitmap(R.mipmap.bronze_scales);
                name = "청동 비늘";
                desc = "피격 시 해당 적에게\r\n내 공격력 만큼의 피해를 줌";
                break;
            case RED_SKULL:
                setBitmap(R.mipmap.red_skull);
                name = "붉은 해골";
                desc = "체력이 50% 이하일 때 공격력 +3";
                break;
            case WAFFLE:
                setBitmap(R.mipmap.waffle);
                name = "와플";
                desc = "획득 시 체력을 모두 회복";
                break;
            case SMOOTH_STONE:
                setBitmap(R.mipmap.smooth_stone);
                name = "매끈한 돌";
                desc = "방어력 +2";
                break;
            case SOZU:
                setBitmap(R.mipmap.sozu);
                name = "소주";
                desc = "공격속도 +10%\r\n총알 발사 방향이 약간 흔들림";
                break;
            case GAMBLING_CHIP:
                setBitmap(R.mipmap.gambling_chip);
                name = "도박 칩";
                desc = "각 총알의 공격력에\r\n-2 ~ +3 값이 더해짐";
                break;
            case WING_BOOTS:
                setBitmap(R.mipmap.wing_boots);
                name = "윙 부츠";
                desc = "이동속도 +30%";
                break;
            case BRIMSTONE:
                setBitmap(R.mipmap.brimstone);
                name = "유황";
                desc = "공격력 +3, 방어력 -1";
                break;
            case MEAT:
                setBitmap(R.mipmap.meat);
                name = "고기 덩어리";
                desc = "체력이 50% 이하일 때\r\n적 처치 시 체력 3 회복";
                break;
            case NINJA_SCROLL:
                setBitmap(R.mipmap.ninja_scroll);
                name = "닌자 두루마리";
                desc = "최종 데미지 -70%, 공격 속도 -100%\r\n총알이 세 갈레로 나감";
                break;
            case CHAMPION_BELT:
                setBitmap(R.mipmap.champion_belt);
                name = "챔피언 벨트";
                desc = "공격 속도 -10%, 이동 속도 -10%\r\n공격력 +3";
                break;
            case TINY_HOUSE:
                setBitmap(R.mipmap.tiny_house);
                name = "작은 집";
                desc = "공격력 +1, 방어력 +1\r\n공격 속도 +5%, 이동 속도 +5%";
                break;
            case BAG_OF_PREP:
                setBitmap(R.mipmap.bag_of_prep);
                name = "준비된 가방";
                desc = "경험치들을 끌어당김";
                break;
            case ASTROLABE:
                setBitmap(R.mipmap.astrolabe);
                name = "아스트롤라베";
                desc = "총알 발사 수 +2\r\n총알이 랜덤한 방향으로 발사됨";
                break;
            case BOTTLED_FLAME:
                setBitmap(R.mipmap.bottled_flame);
                name = "병 속의 불꽃";
                desc = "관통되는 총알을 발사하는\r\n불꽃 정령을 얻음";
                break;
//            case BOTTLED_TORNADO:
//                setBitmap(R.mipmap.bottled_tornado);
//                name = "병 속의 폭풍";
//                desc = "적을 끌어당기는 총알을 발사하는 폭풍 정령을 얻음";
//                break;
            case BOTTLED_LIGHTNING:
                setBitmap(R.mipmap.bottled_lightning);
                name = "병 속의 번개";
                desc = "광역 폭발을 일으키는 총알을\r\n발사하는 번개 정령을 얻음";
                break;
            case NUCLEAR_BATTERY:
                setBitmap(R.mipmap.battery);
                name = "핵 배터리";
                desc = "적을 처치하면 광역 데미지를\r\n주는 폭발을 일으킵니다,";
                break;
            default:
                name = "이름";
                desc = "설명";
                break;
        }
        isActive = false;
        super.update(0.0f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        super.update(0.0f);
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public boolean getActive() {
        return isActive;
    }
}
