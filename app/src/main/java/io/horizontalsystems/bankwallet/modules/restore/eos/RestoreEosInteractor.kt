package io.horizontalsystems.bankwallet.modules.restore.eos

import io.horizontalsystems.bankwallet.core.IClipboardManager
import io.horizontalsystems.eoskit.core.EosUtils

class RestoreEosInteractor(private val clipboardManager: IClipboardManager)
    : RestoreEosModule.IInteractor {

    var delegate: RestoreEosModule.IInteractorDelegate? = null

    override val textFromClipboard: String?
        get() = clipboardManager.getCopiedText()

    override fun validate(accountName: String, privateKey: String) {
        if (accountName.length !in 1..12) {
            delegate?.onInvalidAccount()
            return
        }

        if (privateKey.isEmpty()) {
            delegate?.onInvalidKey()
            return
        }

        try {
            EosUtils.validatePrivateKey(privateKey)
            delegate?.onValidationSuccess(accountName, privateKey)
        } catch (e: Exception) {
            delegate?.onInvalidKey()
        }
    }
}
