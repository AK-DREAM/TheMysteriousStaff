package theWizard.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;

import java.util.Iterator;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import theWizard.DefaultMod;

public class EngineAction extends AbstractGameAction {
    private AbstractPlayer p;
    public int MANA_COST;
    public EngineAction(int kkk) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, 1);
        this.actionType = ActionType.SPECIAL;
        if (kkk == 1) this.duration = Settings.ACTION_DUR_XFAST;
        else this.duration = Settings.ACTION_DUR_FAST;
        this.MANA_COST = kkk;
    }

    public void update() {
        if (CheckManaCostAction.CheckManaCost(this.MANA_COST)) {
            this.addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, 2), 2));
        }
        this.isDone = true;
    }
}

