package br.com.matotvron.tccgymmanagementapp.background.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import br.com.matotvron.tccgymmanagementapp.telas.principaltabfragment.InitialFragment;
import br.com.matotvron.tccgymmanagementapp.telas.principaltabfragment.EquipamentosFragment;
import br.com.matotvron.tccgymmanagementapp.telas.principaltabfragment.RelatoriosFragment;

public class PrincipalActivityViewPager extends FragmentStateAdapter {
    public PrincipalActivityViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public PrincipalActivityViewPager(@NonNull Fragment fragment) {
        super(fragment);
    }

    public PrincipalActivityViewPager(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new InitialFragment();
            case 1:
                return new EquipamentosFragment();
            case 2:
                return new RelatoriosFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
