package contest.blaybus.v1.infrastructure.dto;

enum Coin {
    BronzeDo,
    GoldDo,
    SilverDo,
    ;

    public static Coin calculateCoin(int value) {
        if (value >= 80) {
            return Coin.GoldDo;
        }

        if (value >= 40) {
            return Coin.SilverDo;
        }

        return Coin.BronzeDo;
    }
}

public record QuestWeekResponse(int week, Coin coin, int value, String team, int jobGroup, String name) {

    public static QuestWeekResponse from(final int week, final int value) {
        Coin calculateCoin = Coin.calculateCoin(value);
        return new QuestWeekResponse(week, calculateCoin, value, "음성 1센터", 1, "생산성");
    }

    /*
    [
        {
            "week" : "1"
            "coin" : "GoldDo",
            "value" : "80",
            "team" : "음성 1센터",
            "jobGroup" : "1",
            "name" : "업무개선"
        },
        {
            "week" : "2"
            "coin" : "GoldDo",
            "value" : "80",
            "team" : "음성 1센터",
            "jobGroup" : "1",
            "name" : "생산성"
        },
        {
            "week" : "3"
            "coin" : "SilverDo",
            "value" : "80",
            "team" : "음성 1센터",
            "jobGroup" : "1",
            "name" : "생산성"
        },
        {
            "week" : "4"
            "coin" : "BronzeDo",
            "value" : "40",
            "team" : "음성 1센터",
            "jobGroup" : "1",
            "name" : "생산성"
        },
        {
            "week" : "5"
            "coin" : "SilverDo",
            "value" : "40",
            "team" : "음성 1센터",
            "jobGroup" : "1",
            "name" : "생산성"
        }
    ]
     */

}