public class PlayerNPCInteraction {

    public void answerQuestion(Player player, NPC npc, int answer) {
        if (npc.getQuiz() == null) {
            return;
        }
        if (player.hasMentalBreakdown()) {
            return;
        }
        boolean isCorrect = npc.answerQuestion(answer);
        if (!isCorrect) {
            player.takeMentalDamage(1);
        }
    }
}
