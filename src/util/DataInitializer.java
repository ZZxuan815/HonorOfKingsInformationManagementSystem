package util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import model.Admin;
import model.Equipment;
import model.Hero;
import model.HeroType;
import model.MatchRecord;
import model.Player;
import model.Team;
import service.GameDataManager;

public class DataInitializer {

    public static void init(GameDataManager gdm) {
        initHeroes(gdm);
        initEquipment(gdm);
        initPlayers(gdm);
        initTeams(gdm);
        initMatchRecords(gdm);
    }

    private static void initHeroes(GameDataManager gdm) {
        Hero liBai = new Hero("Li Bai", HeroType.ASSASSIN);
        Map<String, Integer> lbStats = new HashMap<>();
        lbStats.put("hp", 3200); lbStats.put("attack", 200); lbStats.put("defense", 100); lbStats.put("speed", 420);
        liBai.setBaseStats(lbStats);
        liBai.setCompatibleEquipment(Arrays.asList("Shadow Blade", "Endless Blade", "Boots of Resistance"));
        gdm.addHero(liBai);

        Hero diaoChan = new Hero("Diao Chan", HeroType.MAGE);
        Map<String, Integer> dcStats = new HashMap<>();
        dcStats.put("hp", 2800); dcStats.put("attack", 150); dcStats.put("defense", 80); dcStats.put("speed", 380);
        diaoChan.setBaseStats(dcStats);
        diaoChan.setCompatibleEquipment(Arrays.asList("Book of Sages", "Boots of Resistance", "Armor of Thorns"));
        gdm.addHero(diaoChan);

        Hero luBu = new Hero("Lu Bu", HeroType.WARRIOR);
        Map<String, Integer> lbuStats = new HashMap<>();
        lbuStats.put("hp", 3800); lbuStats.put("attack", 220); lbuStats.put("defense", 150); lbuStats.put("speed", 370);
        luBu.setBaseStats(lbuStats);
        luBu.setCompatibleEquipment(Arrays.asList("Shadow Blade", "Armor of Thorns", "Guardian Angel"));
        gdm.addHero(luBu);

        Hero caoCao = new Hero("Cao Cao", HeroType.WARRIOR);
        Map<String, Integer> ccStats = new HashMap<>();
        ccStats.put("hp", 3600); ccStats.put("attack", 210); ccStats.put("defense", 140); ccStats.put("speed", 380);
        caoCao.setBaseStats(ccStats);
        caoCao.setCompatibleEquipment(Arrays.asList("Endless Blade", "Armor of Thorns", "Boots of Resistance"));
        gdm.addHero(caoCao);

        Hero houYi = new Hero("Hou Yi", HeroType.MARKSMAN);
        Map<String, Integer> hyStats = new HashMap<>();
        hyStats.put("hp", 2900); hyStats.put("attack", 240); hyStats.put("defense", 90); hyStats.put("speed", 360);
        houYi.setBaseStats(hyStats);
        houYi.setCompatibleEquipment(Arrays.asList("Shadow Blade", "Endless Blade", "Guardian Angel"));
        gdm.addHero(houYi);

        Hero guanYu = new Hero("Guan Yu", HeroType.WARRIOR);
        Map<String, Integer> gyStats = new HashMap<>();
        gyStats.put("hp", 3700); gyStats.put("attack", 205); gyStats.put("defense", 145); gyStats.put("speed", 390);
        guanYu.setBaseStats(gyStats);
        guanYu.setCompatibleEquipment(Arrays.asList("Shadow Blade", "Armor of Thorns", "Boots of Resistance"));
        gdm.addHero(guanYu);

        Hero zhaoYun = new Hero("Zhao Yun", HeroType.WARRIOR);
        Map<String, Integer> zyStats = new HashMap<>();
        zyStats.put("hp", 3500); zyStats.put("attack", 215); zyStats.put("defense", 130); zyStats.put("speed", 400);
        zhaoYun.setBaseStats(zyStats);
        zhaoYun.setCompatibleEquipment(Arrays.asList("Endless Blade", "Shadow Blade", "Guardian Angel"));
        gdm.addHero(zhaoYun);

        Hero anQiLa = new Hero("An Qi La", HeroType.MAGE);
        Map<String, Integer> aqlStats = new HashMap<>();
        aqlStats.put("hp", 2700); aqlStats.put("attack", 160); aqlStats.put("defense", 70); aqlStats.put("speed", 370);
        anQiLa.setBaseStats(aqlStats);
        anQiLa.setCompatibleEquipment(Arrays.asList("Book of Sages", "Boots of Resistance", "Armor of Thorns"));
        gdm.addHero(anQiLa);

        Hero sunShangXiang = new Hero("Sun Shang Xiang", HeroType.MARKSMAN);
        Map<String, Integer> ssxStats = new HashMap<>();
        ssxStats.put("hp", 2850); ssxStats.put("attack", 245); ssxStats.put("defense", 85); ssxStats.put("speed", 375);
        sunShangXiang.setBaseStats(ssxStats);
        sunShangXiang.setCompatibleEquipment(Arrays.asList("Shadow Blade", "Endless Blade", "Boots of Resistance"));
        gdm.addHero(sunShangXiang);

        Hero hanXin = new Hero("Han Xin", HeroType.ASSASSIN);
        Map<String, Integer> hxStats = new HashMap<>();
        hxStats.put("hp", 3100); hxStats.put("attack", 230); hxStats.put("defense", 95); hxStats.put("speed", 450);
        hanXin.setBaseStats(hxStats);
        hanXin.setCompatibleEquipment(Arrays.asList("Shadow Blade", "Endless Blade", "Guardian Angel"));
        gdm.addHero(hanXin);

        Hero zhuGeLiang = new Hero("Zhu Ge Liang", HeroType.MAGE);
        Map<String, Integer> zglStats = new HashMap<>();
        zglStats.put("hp", 2750); zglStats.put("attack", 180); zglStats.put("defense", 75); zglStats.put("speed", 360);
        zhuGeLiang.setBaseStats(zglStats);
        zhuGeLiang.setCompatibleEquipment(Arrays.asList("Book of Sages", "Boots of Resistance", "Armor of Thorns"));
        gdm.addHero(zhuGeLiang);

        Hero baiLiShouYue = new Hero("Bai Li Shou Yue", HeroType.MARKSMAN);
        Map<String, Integer> blsyStats = new HashMap<>();
        blsyStats.put("hp", 2800); blsyStats.put("attack", 250); blsyStats.put("defense", 80); blsyStats.put("speed", 370);
        baiLiShouYue.setBaseStats(blsyStats);
        baiLiShouYue.setCompatibleEquipment(Arrays.asList("Shadow Blade", "Endless Blade", "Boots of Resistance"));
        gdm.addHero(baiLiShouYue);

        Hero yaSe = new Hero("Ya Se", HeroType.TANK);
        Map<String, Integer> ysStats = new HashMap<>();
        ysStats.put("hp", 4200); ysStats.put("attack", 140); ysStats.put("defense", 200); ysStats.put("speed", 350);
        yaSe.setBaseStats(ysStats);
        yaSe.setCompatibleEquipment(Arrays.asList("Armor of Thorns", "Guardian Angel", "Boots of Resistance"));
        gdm.addHero(yaSe);

        Hero caiWenJi = new Hero("Cai Wen Ji", HeroType.SUPPORT);
        Map<String, Integer> cwjStats = new HashMap<>();
        cwjStats.put("hp", 3000); cwjStats.put("attack", 110); cwjStats.put("defense", 90); cwjStats.put("speed", 380);
        caiWenJi.setBaseStats(cwjStats);
        caiWenJi.setCompatibleEquipment(Arrays.asList("Book of Sages", "Armor of Thorns", "Boots of Resistance"));
        gdm.addHero(caiWenJi);

        Hero liYuanFang = new Hero("Li Yuan Fang", HeroType.MARKSMAN);
        Map<String, Integer> lyfStats = new HashMap<>();
        lyfStats.put("hp", 2750); lyfStats.put("attack", 235); lyfStats.put("defense", 85); lyfStats.put("speed", 365);
        liYuanFang.setBaseStats(lyfStats);
        liYuanFang.setCompatibleEquipment(Arrays.asList("Shadow Blade", "Endless Blade", "Guardian Angel"));
        gdm.addHero(liYuanFang);

        Hero lianPo = new Hero("Lian Po", HeroType.TANK);
        Map<String, Integer> lpStats = new HashMap<>();
        lpStats.put("hp", 4500); lpStats.put("attack", 130); lpStats.put("defense", 220); lpStats.put("speed", 340);
        lianPo.setBaseStats(lpStats);
        lianPo.setCompatibleEquipment(Arrays.asList("Armor of Thorns", "Guardian Angel", "Boots of Resistance"));
        gdm.addHero(lianPo);

        Hero sunBin = new Hero("Sun Bin", HeroType.SUPPORT);
        Map<String, Integer> sbStats = new HashMap<>();
        sbStats.put("hp", 2950); sbStats.put("attack", 120); sbStats.put("defense", 85); sbStats.put("speed", 390);
        sunBin.setBaseStats(sbStats);
        sunBin.setCompatibleEquipment(Arrays.asList("Book of Sages", "Armor of Thorns", "Boots of Resistance"));
        gdm.addHero(sunBin);

        Hero moYe = new Hero("Mo Ye", HeroType.JUNGLER);
        Map<String, Integer> myStats = new HashMap<>();
        myStats.put("hp", 3300); myStats.put("attack", 225); myStats.put("defense", 110); myStats.put("speed", 430);
        moYe.setBaseStats(myStats);
        moYe.setCompatibleEquipment(Arrays.asList("Shadow Blade", "Endless Blade", "Guardian Angel"));
        gdm.addHero(moYe);
    }

    private static void initEquipment(GameDataManager gdm) {
        gdm.addEquipment(new Equipment("Shadow Blade", "Weapon", "+80 Attack, +15% Crit Rate"));
        gdm.addEquipment(new Equipment("Endless Blade", "Weapon", "+100 Attack, +20% Crit Rate, +50% Crit Damage"));
        gdm.addEquipment(new Equipment("Book of Sages", "Magic", "+200 Magic Power, +500 HP"));
        gdm.addEquipment(new Equipment("Armor of Thorns", "Armor", "+240 Armor, Reflects 15% Physical Damage"));
        gdm.addEquipment(new Equipment("Boots of Resistance", "Boots", "+110 Armor, +35% Tenacity"));
        gdm.addEquipment(new Equipment("Guardian Angel", "Armor", "+140 Armor, Revive upon death"));
        gdm.addEquipment(new Equipment("Radiant Blade", "Weapon", "+70 Attack, +10% Cooldown"));
        gdm.addEquipment(new Equipment("Storm Sword", "Weapon", "+90 Attack, +10% Lifesteal"));
        gdm.addEquipment(new Equipment("Frozen Staff", "Magic", "+160 Magic Power, +15% Slow Effect"));
        gdm.addEquipment(new Equipment("Echo Wand", "Magic", "+180 Magic Power, +7% Movement Speed"));
        gdm.addEquipment(new Equipment("Doom Shield", "Armor", "+300 Armor, +1200 HP"));
        gdm.addEquipment(new Equipment("Mantle of the Sages", "Armor", "+200 Armor, +800 HP, +100 Magic Resist"));
        gdm.addEquipment(new Equipment("Boots of Speed", "Boots", "+60 Movement Speed"));
        gdm.addEquipment(new Equipment("Boots of Mana", "Boots", "+60 Movement Speed, +500 Mana"));
        gdm.addEquipment(new Equipment("Rampage Cloak", "Armor", "+150 Armor, +20% Crit Resistance"));
        gdm.addEquipment(new Equipment("Void Staff", "Magic", "+140 Magic Power, +40% Magic Pierce"));
        gdm.addEquipment(new Equipment("Bloodthirsty Blade", "Weapon", "+80 Attack, +25% Lifesteal"));
        gdm.addEquipment(new Equipment("Sky Breaker", "Weapon", "+120 Attack, +5% Max HP True Damage"));
        gdm.addEquipment(new Equipment("Moonlight Shield", "Armor", "+180 Armor, +100 Magic Resist, +600 HP"));
        gdm.addEquipment(new Equipment("Sacrifice Dagger", "Weapon", "+60 Attack, +10% Attack Speed, +8% Lifesteal"));
        gdm.addEquipment(new Equipment("Phoenix Crown", "Magic", "+220 Magic Power, +10% Cooldown, +300 Mana"));
        gdm.addEquipment(new Equipment("Purgatory Boots", "Boots", "+60 Movement Speed, +15 Magic Pierce"));
    }

    private static void initPlayers(GameDataManager gdm) {
        Player p1 = new Player("P001", "Li Bai", "pass123", 35, 0.68, "T01");
        p1.setOwnedHeroes(Arrays.asList("Li Bai", "Han Xin", "Zhao Yun"));
        gdm.addPlayer(p1);

        Player p2 = new Player("P002", "Diao Chan", "pass456", 28, 0.55, "T01");
        p2.setOwnedHeroes(Arrays.asList("Diao Chan", "An Qi La", "Zhu Ge Liang"));
        gdm.addPlayer(p2);

        Player p3 = new Player("P003", "Cao Cao", "pass789", 42, 0.72, "T01");
        p3.setOwnedHeroes(Arrays.asList("Cao Cao", "Lu Bu", "Guan Yu"));
        gdm.addPlayer(p3);

        Player p4 = new Player("P004", "Hou Yi", "pass111", 20, 0.45, "T02");
        p4.setOwnedHeroes(Arrays.asList("Hou Yi", "Sun Shang Xiang", "Bai Li Shou Yue"));
        gdm.addPlayer(p4);

        Player p5 = new Player("P005", "Lu Bu", "pass222", 48, 0.78, "T02");
        p5.setOwnedHeroes(Arrays.asList("Lu Bu", "Zhao Yun", "Cao Cao"));
        gdm.addPlayer(p5);

        Player p6 = new Player("P006", "Zhao Yun", "pass333", 30, 0.60, "T02");
        p6.setOwnedHeroes(Arrays.asList("Zhao Yun", "Mo Ye", "Han Xin"));
        gdm.addPlayer(p6);

        Player p7 = new Player("P007", "Guan Yu", "pass444", 38, 0.65, "T03");
        p7.setOwnedHeroes(Arrays.asList("Guan Yu", "Lu Bu", "Lian Po"));
        gdm.addPlayer(p7);

        Player p8 = new Player("P008", "Han Xin", "pass555", 45, 0.82, "T03");
        p8.setOwnedHeroes(Arrays.asList("Han Xin", "Mo Ye", "Li Bai"));
        gdm.addPlayer(p8);

        Player p9 = new Player("P009", "Ya Se", "pass666", 25, 0.50, "T03");
        p9.setOwnedHeroes(Arrays.asList("Ya Se", "Lian Po", "Cai Wen Ji"));
        gdm.addPlayer(p9);

        Player p10 = new Player("P010", "Mo Ye", "pass777", 33, 0.70, "T01");
        p10.setOwnedHeroes(Arrays.asList("Mo Ye", "Han Xin", "Li Bai"));
        gdm.addPlayer(p10);

        Player p11 = new Player("P011", "Sun Shang Xiang", "pass888", 40, 0.75, "T01");
        p11.setOwnedHeroes(Arrays.asList("Sun Shang Xiang", "Hou Yi", "Zhao Yun"));
        gdm.addPlayer(p11);

        Player p12 = new Player("P012", "Zhu Ge Liang", "pass999", 36, 0.62, "T02");
        p12.setOwnedHeroes(Arrays.asList("Zhu Ge Liang", "Diao Chan", "An Qi La"));
        gdm.addPlayer(p12);

        Player p13 = new Player("P013", "Lian Po", "pass000", 44, 0.58, "T02");
        p13.setOwnedHeroes(Arrays.asList("Lian Po", "Ya Se", "Cai Wen Ji"));
        gdm.addPlayer(p13);

        Player p14 = new Player("P014", "Cai Wen Ji", "passaaa", 32, 0.48, "T03");
        p14.setOwnedHeroes(Arrays.asList("Cai Wen Ji", "Sun Bin", "Diao Chan"));
        gdm.addPlayer(p14);

        Player p15 = new Player("P015", "Li Yuan Fang", "passbbb", 38, 0.66, "T03");
        p15.setOwnedHeroes(Arrays.asList("Li Yuan Fang", "Bai Li Shou Yue", "Sun Shang Xiang"));
        gdm.addPlayer(p15);

        Admin admin1 = new Admin("A001", "AdminZhang", "admin123", "super_admin");
        gdm.addAdmin(admin1);

        Admin admin2 = new Admin("A002", "AdminLi", "admin456", "data_manager");
        gdm.addAdmin(admin2);
    }

    private static void initTeams(GameDataManager gdm) {
        Team t1 = new Team("T01", "Dragon Legion");
        t1.setMemberIds(Arrays.asList("P001", "P002", "P003", "P010", "P011"));
        t1.setTotalMatches(75);
        t1.setWins(48);
        gdm.addTeam(t1);

        Team t2 = new Team("T02", "Phoenix Rising");
        t2.setMemberIds(Arrays.asList("P004", "P005", "P006", "P012", "P013"));
        t2.setTotalMatches(70);
        t2.setWins(38);
        gdm.addTeam(t2);

        Team t3 = new Team("T03", "Tiger Guard");
        t3.setMemberIds(Arrays.asList("P007", "P008", "P009", "P014", "P015"));
        t3.setTotalMatches(65);
        t3.setWins(35);
        gdm.addTeam(t3);
    }

    private static void initMatchRecords(GameDataManager gdm) {
        Map<String, String> picks1 = new HashMap<>();
        picks1.put("P001", "Li Bai");
        picks1.put("P002", "Diao Chan");
        picks1.put("P003", "Cao Cao");
        picks1.put("P010", "Mo Ye");
        MatchRecord m1 = new MatchRecord("M001", "2026-01-15", "T01", "T02", "T01");
        m1.setHeroPicks(picks1);
        gdm.addMatchRecord(m1);

        Map<String, String> picks2 = new HashMap<>();
        picks2.put("P004", "Hou Yi");
        picks2.put("P005", "Lu Bu");
        picks2.put("P006", "Zhao Yun");
        MatchRecord m2 = new MatchRecord("M002", "2026-01-18", "T02", "T03", "T02");
        m2.setHeroPicks(picks2);
        gdm.addMatchRecord(m2);

        Map<String, String> picks3 = new HashMap<>();
        picks3.put("P007", "Guan Yu");
        picks3.put("P008", "Han Xin");
        picks3.put("P009", "Ya Se");
        MatchRecord m3 = new MatchRecord("M003", "2026-01-20", "T03", "T01", "T01");
        m3.setHeroPicks(picks3);
        gdm.addMatchRecord(m3);

        Map<String, String> picks4 = new HashMap<>();
        picks4.put("P001", "Han Xin");
        picks4.put("P003", "Lu Bu");
        picks4.put("P010", "Li Bai");
        picks4.put("P002", "An Qi La");
        MatchRecord m4 = new MatchRecord("M004", "2026-02-01", "T01", "T03", "T01");
        m4.setHeroPicks(picks4);
        gdm.addMatchRecord(m4);

        Map<String, String> picks5 = new HashMap<>();
        picks5.put("P004", "Sun Shang Xiang");
        picks5.put("P005", "Zhao Yun");
        picks5.put("P006", "Mo Ye");
        MatchRecord m5 = new MatchRecord("M005", "2026-02-05", "T02", "T01", "T02");
        m5.setHeroPicks(picks5);
        gdm.addMatchRecord(m5);

        Map<String, String> picks6 = new HashMap<>();
        picks6.put("P007", "Lian Po");
        picks6.put("P008", "Li Bai");
        picks6.put("P009", "Cai Wen Ji");
        MatchRecord m6 = new MatchRecord("M006", "2026-02-10", "T03", "T02", "T03");
        m6.setHeroPicks(picks6);
        gdm.addMatchRecord(m6);

        Map<String, String> picks7 = new HashMap<>();
        picks7.put("P001", "Zhao Yun");
        picks7.put("P002", "Zhu Ge Liang");
        picks7.put("P003", "Guan Yu");
        picks7.put("P010", "Han Xin");
        MatchRecord m7 = new MatchRecord("M007", "2026-02-20", "T01", "T02", "T01");
        m7.setHeroPicks(picks7);
        gdm.addMatchRecord(m7);

        Map<String, String> picks8 = new HashMap<>();
        picks8.put("P004", "Bai Li Shou Yue");
        picks8.put("P005", "Cao Cao");
        picks8.put("P006", "Han Xin");
        MatchRecord m8 = new MatchRecord("M008", "2026-03-01", "T02", "T03", "T03");
        m8.setHeroPicks(picks8);
        gdm.addMatchRecord(m8);

        Map<String, String> picks9 = new HashMap<>();
        picks9.put("P007", "Lu Bu");
        picks9.put("P008", "Mo Ye");
        picks9.put("P009", "Sun Bin");
        MatchRecord m9 = new MatchRecord("M009", "2026-03-05", "T03", "T01", "T01");
        m9.setHeroPicks(picks9);
        gdm.addMatchRecord(m9);

        Map<String, String> picks10 = new HashMap<>();
        picks10.put("P001", "Li Bai");
        picks10.put("P003", "Cao Cao");
        picks10.put("P005", "Lu Bu");
        picks10.put("P008", "Han Xin");
        MatchRecord m10 = new MatchRecord("M010", "2026-03-10", "T01", "T03", "T01");
        m10.setHeroPicks(picks10);
        gdm.addMatchRecord(m10);

        Map<String, String> picks11 = new HashMap<>();
        picks11.put("P002", "Diao Chan");
        picks11.put("P004", "Hou Yi");
        picks11.put("P007", "Guan Yu");
        picks11.put("P009", "Ya Se");
        MatchRecord m11 = new MatchRecord("M011", "2026-03-15", "T02", "T03", "T02");
        m11.setHeroPicks(picks11);
        gdm.addMatchRecord(m11);
    }
}
