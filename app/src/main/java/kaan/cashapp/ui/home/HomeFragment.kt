package kaan.cashapp.ui.home

import android.app.AlertDialog
import android.content.Context.*
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle.State.RESUMED
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import dagger.hilt.android.AndroidEntryPoint
import kaan.cashapp.R
import kaan.cashapp.base.DataBindFragment
import kaan.cashapp.databinding.FragmentHomeBinding
import kaan.cashapp.extra.Result
import kaan.cashapp.extra.gone
import kaan.cashapp.extra.launchAndCollectIn
import kaan.cashapp.extra.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : DataBindFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val vm: HomeViewModel by hiltNavGraphViewModels(R.id.navigation_home)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.repository.stocks.launchAndCollectIn(viewLifecycleOwner, RESUMED) {
            when (it) {
                is Result.Success -> {
                    Log.d("XXX", "success")

                    val flag = requireActivity().getSharedPreferences("myPref", MODE_PRIVATE).getBoolean("flag001", false)

                    if (!flag) {
                        vm.setStocks(it.data)

                        requireActivity().getSharedPreferences("myPref", MODE_PRIVATE).edit().putBoolean("flag001", true).apply()
                        showSuccessAlert()
                        binding.loader.gone()
                        hideAll()

                    } else {
                        errorState()
                    }
                }

                is Result.Loading -> {
                    showLoader(true)
                }

                is Result.NoData -> {
                    showLoader(false)
                    showNoData(true)
                }

                is Result.Error -> {
                    Log.d("XXX", "[Kaan] error ")
                    showError(true)
                    showLoader(false)

                }
            }
        }

        binding.stocksList.addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))

        binding.register.setOnClickListener {
            Log.d("XXX", "Register clicked ! [ ${binding.name.text} ] [ ${binding.email.text} ] ")

            inputValidateAndSubmit()
        }
    }

    private fun errorState() {
        binding.loader.gone()
        binding.form.gone()
        binding.textError.visible()
        binding.textError.text = getString(R.string.already_registered)
    }

    private fun inputValidateAndSubmit() {
        binding.email.text?.let { emailText ->
            if (binding.name.text.isNullOrBlank())
                binding.nameLayout.error = "Please enter your name"
            else {
                binding.nameLayout.error = null
                if (!(emailText.contains('@') && emailText.isNotBlank() && emailText.contains('.')))
                    binding.emailLayout.error = "Please enter a correct email address !"
                else {
                    fetchStocks(binding.name.text.toString(), binding.email.text.toString())
                }
            }
        }
    }

    private fun showSuccessAlert() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.view_congrats, null)
        builder.setView(dialogView)
        builder.create().show()
    }

    /**
     * Initial call to get the stocks.
     */
    private fun fetchStocks(name: String, email: String) {
        binding.textError.gone()

        lifecycleScope.launch(Dispatchers.IO) {
            vm.repository.fetchStocks(name, email)
        }
    }

    // region Private Helpers
    /**
     * Hide the Loader and Error indicators.
     *
     */
    private fun hideAll() {
        binding.form.gone()
        showError(false)
        showNoData(false)
        showLoader(false)
    }

    /**
     * Set visibility to flag parameter, or toggle the visibility
     * when used without parameters.
     *
     * @param visibilityFlag The boolean flag, if passed, enforces visibility by flag.
     */
    private fun showLoader(visibilityFlag: Boolean? = null) {
        binding.loader.visible(visibilityFlag ?: !binding.loader.isVisible)
    }

    private fun showError(flag: Boolean? = null) {
        binding.textError.visible(flag ?: true)
    }

    private fun showNoData(flag: Boolean? = null) {
        binding.textNoData.visible(flag ?: true)
    }
    // endregion
}
