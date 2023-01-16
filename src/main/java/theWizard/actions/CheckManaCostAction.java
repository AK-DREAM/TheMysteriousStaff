package theWizard.actions;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWizard.cards.*;

import java.util.ArrayList;
import java.util.Iterator;

public class CheckManaCostAction {
    public static boolean CheckManaCost(int CST, boolean trigger_effect) {
        AbstractPlayer p = AbstractDungeon.player;
        Iterator it = p.hand.group.iterator();
        int cnt = 0;
        while (it.hasNext()) {
            AbstractCard c = (AbstractCard)it.next();
            if (c instanceof Mana) {
                ++cnt;
            }
        }
        if (p.hasPower("theWizard:EfficientPower")) --CST;
        if (p.hasPower("theWizard:NoCostPower") || cnt >= CST) {
            ArrayList<AbstractCard> cardsToExhaust = new ArrayList();
            it = p.hand.group.iterator();
            AbstractCard c;
            if (p.hasPower("theWizard:NoCostPower") && CST > 0) {
                AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, "theWizard:NoCostPower", 1));
                cnt = 0;
            } else cnt = CST;
            while (it.hasNext()) {
                if (cnt == 0) break;
                c = (AbstractCard)it.next();
                if (c instanceof Mana && !c.upgraded) {
                    cardsToExhaust.add(c);
                    --cnt;
                }
            }
            it = p.hand.group.iterator();
            while (it.hasNext()) {
                if (cnt == 0) break;
                c = (AbstractCard)it.next();
                if (c instanceof Mana) {
                    cardsToExhaust.add(c);
                    --cnt;
                }
            }
            assert(cnt == 0);
            it = cardsToExhaust.iterator();
            while (it.hasNext()) {
                c = (AbstractCard)it.next();
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            }

            if (trigger_effect) {
                // Card: Mastery
                it = p.discardPile.group.iterator();
                while (it.hasNext()) {
                    c = (AbstractCard)it.next();
                    if (c instanceof Mastery) {
                        AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(c));
                    }
                }
                // Card: Mastery

                // Relic: Cloak
                if (p.hasRelic("theWizard:Cloak")) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, 4));
                }
                // Relic: Cloak
            }

            return true;
        } else return false;
    }
    public static boolean CheckManaCost(int CST) {
        return CheckManaCost(CST, true);
    }
}
