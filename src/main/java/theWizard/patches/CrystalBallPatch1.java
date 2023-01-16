package theWizard.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theWizard.powers.ColdPower;

import java.util.Iterator;

public class CrystalBallPatch1 {
    public CrystalBallPatch1() {
    }

    @SpirePatch(
            clz = GainBlockAction.class,
            method = "update",
            paramtypez = {

            }
    )
    public static class CrystalBallPatch1Patch {
        @SpireInsertPatch(
                rloc = 1,
                localvars = {"this.target", "this.amount"}
        )
        public static SpireReturn<Void> Insert(GainBlockAction cc, AbstractCreature target, int amount) {
            AbstractPlayer p = AbstractDungeon.player;
            if (target.isPlayer && p.hasRelic("theWizard:CrystalBall")) {
                p.getRelic("theWizard:CrystalBall").flash();
                int mx = -1; AbstractMonster mo = null;
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (!m.isDeadOrEscaped() && !m.halfDead) {
                        AbstractPower cold = m.getPower("theWizard:ColdPower");
                        int cnt = (cold == null) ? 0 : cold.amount;
                        if (mx == -1 || cnt < mx) {
                            mx = cnt; mo = m;
                        }
                    }
                }
                if (mo != null) {
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, p, new ColdPower(mo, p, amount)));
                }
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    c.applyPowers();
                }
                cc.isDone = true;
                return SpireReturn.Return();
            } else {
                return SpireReturn.Continue();
            }
        }
    }

}
