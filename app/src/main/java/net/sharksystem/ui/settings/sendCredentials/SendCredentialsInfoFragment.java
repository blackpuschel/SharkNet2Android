package net.sharksystem.ui.settings.sendCredentials;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sharksystem.R;
import net.sharksystem.databinding.FragmentSendCredentialsInfoBinding;

/**
 * This fragment informs the user about the earnestness of exchanging credentials and lets the
 * sender add a Custom Identification Code that the sender told him to add, so he can identify
 * the sender.
 */
public class SendCredentialsInfoFragment extends Fragment {

    /**
     * Binding for easy access to layout elements
     */
   private FragmentSendCredentialsInfoBinding binding;

    /**
     * View Model for saving the credential message for the fragment, where it is really send
     */
   private CredentialsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentSendCredentialsInfoBinding.inflate(this.getLayoutInflater());

        this.viewModel = new ViewModelProvider(this.requireActivity()).get(CredentialsViewModel.class);

        //Abort button brings the user back to the settings overview screen
        this.binding.fragmentSendCredentialsInfoAbortButton.setOnClickListener(view ->
                Navigation.findNavController(view)
                        .navigate(R.id.action_nav_send_credentials_info_to_nav_settings)
        );

        //If the user continues, the credentials will be saved including optional CIC
        this.binding.fragmentSendCredentialsInfoContinueButton.setOnClickListener(view -> {
            this.viewModel.setCIC(this.binding.fragmentSendCredentialsInfoCicInput.getText().
                    toString());

            Navigation.findNavController(view).
                    navigate(R.id.action_nav_send_credentials_info_to_nav_send_credentials);
        });

        return this.binding.getRoot();
    }
}