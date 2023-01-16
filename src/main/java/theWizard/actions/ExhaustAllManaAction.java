package theWizard.actions;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWizard.cards.Mana;

import java.util.ArrayList;
import java.util.Iterator;

public class ExhaustAllManaAction {
    public static int ExhaustAllManaAction(int maxn) {
        AbstractPlayer p = AbstractDungeon.player;
        Iterator it = p.hand.group.iterator();
        ArrayList<AbstractCard> Manas = new ArrayList();
        AbstractCard c;
        int cnt = 0;
        while (it.hasNext()) {
            c = (AbstractCard)it.next();
            if (c instanceof Mana && cnt < maxn) {
                ++cnt; Manas.add(c);
            }
        }
        it = Manas.iterator();
        while (it.hasNext()) {
            c = (AbstractCard)it.next();
            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
        }
        return cnt;
    }
}
