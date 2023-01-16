package theWizard.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
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
import theWizard.DefaultMod;
import theWizard.cards.FrostAttack;
import theWizard.powers.ColdPower;

import java.util.Iterator;

public class FrostAttackPatch {
    public FrostAttackPatch() {
    }

    @SpirePatch(
            clz = UseCardAction.class,
            method = "update",
            paramtypez = {

            }
    )
    public static class FrostAttackPatchPatch {
        @SpireInsertPatch(
                loc = 136,
                localvars = {"this.reboundCard"}
        )
        public static void Insert(UseCardAction cc, AbstractCard ___targetCard, @ByRef boolean[] reboundCard) {
            if (___targetCard instanceof FrostAttack) {
                reboundCard[0] = true;
                DefaultMod.logger.info("True!");
            } else DefaultMod.logger.info("False!");
        }
    }

}
