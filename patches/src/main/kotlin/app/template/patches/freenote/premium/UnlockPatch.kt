package app.morphe.patches.freenote.premium.v3250

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.fingerprint.MethodFingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions

object PremiumStatusFingerprint : MethodFingerprint(
    strings = listOf("premium_status", "is_premium", "isPremium", "subscribed", "pro_user"),
    customFingerprint = { method, _ -> method.returnType == "Z" }
)

object FreeLimitFingerprint : MethodFingerprint(
    strings = listOf("note_limit", "free_limit", "max_free_notes", "reached_limit"),
    customFingerprint = { method, _ -> method.returnType == "Z" || method.returnType == "I" }
)

object AdManagerFingerprint : MethodFingerprint(
    strings = listOf("googleads", "admob", "AdsManager", "showAd", "loadInterstitial"),
)

val freenoteV3250Patch = bytecodePatch(
    name = "Freenotes 3.25.0 - Unlock Premium & Remove 3-note limit",
    description = "Targets ai.note.notepad.memo.journal.freenote v3.25.0. Forces premium true, sets max notes to unlimited, disables limit dialog."
) {
    compatibleWith(
        "ai.note.notepad.memo.journal.freenote" to "3.25.0",
        "ai.note.notepad.memo.journal.freenote" to "3.24.5"
    )
    execute {
        PremiumStatusFingerprint.result?.let { res ->
            res.mutableMethod.apply {
                addInstructions(0, """
                    const/4 v0, 0x1
                    return v0
                """.trimIndent())
            }
        }
        FreeLimitFingerprint.result?.let { res ->
            res.mutableMethod.apply {
                if (res.mutableMethod.returnType == "Z") {
                    addInstructions(0, """
                        const/4 v0, 0x0
                        return v0
                    """.trimIndent())
                } else {
                    addInstructions(0, """
                        const v0, 0x7fffffff
                        return v0
                    """.trimIndent())
                }
            }
        }
        AdManagerFingerprint.result?.let { res ->
            res.mutableMethod.apply {
                addInstructions(0, "return-void")
            }
        }
    }
}
