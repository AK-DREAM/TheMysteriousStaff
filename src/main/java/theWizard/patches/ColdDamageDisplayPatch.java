package theWizard.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BobEffect;
import theWizard.powers.ColdPower;

public class ColdDamageDisplayPatch {
    public ColdDamageDisplayPatch() {
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "renderDamageRange",
            paramtypez = {SpriteBatch.class}
    )
    public static class CheckColdPowerMonster {
        @SpireInsertPatch(
                rloc = 0,
                localvars = {}
        )
        public static SpireReturn<Void> Insert(AbstractMonster mo, SpriteBatch sb, boolean ___isMultiDmg, int ___intentDmg, int ___intentMultiAmt, BobEffect ___bobEffect, Color ___intentColor) {
            AbstractPower cc = mo.getPower("theWizard:ColdPower");
            if (cc == null) return SpireReturn.Continue();

            if (mo.intent.name().contains("ATTACK")) {
                int amt = cc.amount;
                if (___isMultiDmg) {
                    amt = Math.min(amt, ___intentDmg*___intentMultiAmt);
                    FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, ___intentDmg + "x" + ___intentMultiAmt + "(-" + amt + ")", mo.intentHb.cX - 30.0F * Settings.scale, mo.intentHb.cY + ___bobEffect.y - 12.0F * Settings.scale, ___intentColor);
                } else {
                    amt = Math.min(amt, ___intentDmg);
                    FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, ___intentDmg + "(-" + amt + ")", mo.intentHb.cX - 30.0F * Settings.scale, mo.intentHb.cY + ___bobEffect.y - 12.0F * Settings.scale, ___intentColor);
                }
            }
            return SpireReturn.Return();
        }
    }

}
