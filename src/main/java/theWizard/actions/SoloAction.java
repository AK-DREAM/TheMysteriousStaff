package theWizard.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import theWizard.DefaultMod;

public class SoloAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    public SoloAction() {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, 1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (p.hand.size() == 0) {
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                if (AbstractDungeon.handCardSelectScreen.selectedCards.size() != 0) {
                    DefaultMod.logger.info(AbstractDungeon.handCardSelectScreen.selectedCards.size());
                    AbstractCard c;
                    ArrayList<AbstractCard> tmp = new ArrayList();
                    for(Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); tmp.add(c)) {
                        c = (AbstractCard)var1.next();
                        c.setCostForTurn(0);
                    }
                    Iterator it = p.hand.group.iterator();
                    while (it.hasNext()) {
                        c = (AbstractCard)it.next();
                        this.addToBot(new DiscardSpecificCardAction(c));
                    }
                    it = tmp.iterator();
                    while (it.hasNext()) {
                        c = (AbstractCard)it.next();
                        AbstractDungeon.player.hand.addToTop(c);
                    }
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("RetainCardsAction");
        TEXT = uiStrings.TEXT;
    }
}

