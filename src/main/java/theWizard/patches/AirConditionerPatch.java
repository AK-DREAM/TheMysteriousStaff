package theWizard.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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

public class AirConditionerPatch {
    public AirConditionerPatch() {
    }

    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    AbstractCreature.class,
                    AbstractCreature.class,
                    AbstractPower.class,
                    int.class,
                    boolean.class,
                    AbstractGameAction.AttackEffect.class
            }
    )
    public static class AirConditionerPatchPatch {
        @SpireInsertPatch(
                rloc = 16,
                localvars = {"this.powerToApply.amount", "this.amount"}
        )
        public static void Insert(ApplyPowerAction cc, AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect, @ByRef int[] a, @ByRef int[] b) {
            if (AbstractDungeon.player.hasRelic("theWizard:AirConditioner") && source != null && source.isPlayer && target != source && powerToApply.ID.equals("theWizard:ColdPower")) {
                AbstractDungeon.player.getRelic("theWizard:AirConditioner").flash();
                ++a[0]; ++b[0];
            }
        }
    }

}
