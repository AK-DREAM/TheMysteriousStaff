package theWizard.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWizard.powers.ColdPower;

public class SpellBookOfFrostAction extends AbstractGameAction {
    private int cc;
    public SpellBookOfFrostAction(int cnt) {
        this.duration = 0.001F;
        cc = cnt;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.05F));
        this.tickDuration();
        AbstractPlayer p = AbstractDungeon.player;
        if (this.isDone) {
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (c.type == AbstractCard.CardType.SKILL) {
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        this.addToBot(new ApplyPowerAction(mo, p,
                                new ColdPower(mo, p, cc)));
                    }
                }
            }
        }
    }
}
