package theWizard.actions;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWizard.cards.*;

public class AddManaAction {
    public static void AddManaAction(int cnt, boolean isupgraded) {
        AbstractDynamicCard c = new Mana();
        if (isupgraded) {
            c.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, cnt));
    }
}
