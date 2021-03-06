package layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.MainActivity;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.SplashScreenActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentConfiguracion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentConfiguracion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentConfiguracion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentConfiguracion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentConfiguracion.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentConfiguracion newInstance(String param1, String param2) {
        FragmentConfiguracion fragment = new FragmentConfiguracion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
private Switch sw_sonido;

    EditText url_base, dom;
    Button b_save;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_configuracion, container, false);
        // Inflate the layout for this fragment

        sw_sonido = (Switch) rootView.findViewById(R.id.switch_s);
        Boolean switchState = sw_sonido.isChecked();

        url_base = (EditText) rootView.findViewById(R.id.url_base);
        dom = (EditText) rootView.findViewById(R.id.dom);

        b_save = (Button) rootView.findViewById(R.id.b_save);

      //



        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // boolean isValidUrl(urlvalido);


                //boolean est=URLUtil.isValidUrl("https:/app.antapaccay.com.pe/proportal/scom_service/api");

                String a = url_base.getText().toString().replace(" ","");

                boolean est=URLUtil.isValidUrl(url_base.getText().toString());
                String ultimo = a.substring(a.length() - 1);





                if(est==true&ultimo.equals("/")){
                Registrar(v);
                Recuperar_data();
                //getActivity().getFragmentManager().popBackStack();
                getActivity().finish();
                GlobalVariables.cont_alert=1;////////////////////////////
                Intent intent = new Intent(getActivity(), SplashScreenActivity.class);
                startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "la url es incorrecta", Toast.LENGTH_SHORT).show();

                }


            }
        });

        //sharedpreference para url del servidor
        SharedPreferences url_save = this.getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        url_base.setText(url_save.getString("url", ""));

        //sharedpreference para url del dominio
        SharedPreferences dominio = this.getActivity().getSharedPreferences("dom", Context.MODE_PRIVATE);
        dom.setText(dominio.getString("domain", ""));





        return rootView;

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void Registrar(View v) {



        SharedPreferences url_save = this.getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences dominio = this.getActivity().getSharedPreferences("dom", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = url_save.edit();
        editor.putString("url", url_base.getText().toString());

        SharedPreferences.Editor editor2 = dominio.edit();
        editor2.putString("domain", dom.getText().toString());

        editor.commit();
        editor2.commit();

        //v.finish();


    }


    public void Recuperar_data() {

        SharedPreferences settings =  this.getActivity().getSharedPreferences("dom", Context.MODE_PRIVATE);
        String dominio_user = settings.getString("domain","valorpordefecto");
        //Toast.makeText(this.getActivity(), nombre, Toast.LENGTH_SHORT).show();

        Toast.makeText(this.getActivity(),"Se guardaron los cambios", Toast.LENGTH_SHORT).show();
    }


}
