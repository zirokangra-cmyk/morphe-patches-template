package app.morphe.patches.freenote.ads

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.fingerprint.MethodFingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions

object AdsInitFingerprint : MethodFingerprint(
    strings = listOf("com.google.android.gms.ads.MobileAds", "initialize", "AdMob"),
)

val removeAdsPatch = bytecodePatch(
    name = "Remove Ads - Freenotes 3.25.0",
    description = "Removes banner, interstitial, native and rewarded ads."
) {
    compatibleWith("ai.note.notepad.memo.journal.freenote")
    execute {
        AdsInitFingerprint.result?.let { result ->
            result.mutableMethod.apply {
                addInstructions(0, "return-void")
            }
        }
    }
}

