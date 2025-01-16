package contest.blaybus.v1.infrastructure.dto;

public record QuestMonthResponse(int month, Coin coin, int value, String team, int jobGroup, String name) {
    public static QuestMonthResponse from(final int month, final int value) {
        Coin calculateCoin = Coin.calculateCoin(value);
        return new QuestMonthResponse(month, calculateCoin, value, "음성 1센터", 1, "생산성");
    }

}