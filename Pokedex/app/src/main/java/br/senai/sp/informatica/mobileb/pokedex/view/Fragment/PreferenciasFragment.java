package br.senai.sp.informatica.mobileb.pokedex.view.Fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import br.senai.sp.informatica.mobileb.pokedex.R;

/**
 * Created by WEB on 12/01/2018.
 */

public class PreferenciasFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
