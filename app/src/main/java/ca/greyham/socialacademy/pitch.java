package ca.greyham.socialacademy;

import android.content.Context;
import android.drm.DrmManagerClient;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Pitch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Pitch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pitch extends Fragment implements YouTubePlayer.OnInitializedListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PITCH_COMPANY = "param1";
    private static final String PITCH_BLURB = "param2";
    private static final String PITCH_CAMPAIGN = "param3";
    private static final String PITCH_SPONSOR = "param5";
    private static final String VIDEO_URL = "param4";
    private static final String DEVELOPER_KEY = "AIzaSyCWqS5ve4UI0VJT61nBCy4icrsDqQzF2tI";

    private String mPitchCompany;
    private String mPitchBlurb;
    private String mPitchCampaignName;
    private String mPitchSponsor;
    private String mVideoURL;

    private YouTubePlayer ytPlayer;
    private YouTubePlayerView youTubeView;


    private OnFragmentInteractionListener mListener;

    public Pitch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pitchCompany The name of the Pitch company.
     * @param pitchBlurb The blurb of the Pitch.
     * @param pitchCampaignName
     *@param videoURL
     * @param pitchSponsor
     * @return A new instance of fragment Pitch.
     */

    //TODO: set video source
    //TODO: Set video blurb

    public static Pitch newInstance(String pitchCompany, String pitchBlurb, String pitchCampaignName, String pitchSponsor, String videoURL) {
        Pitch fragment = new Pitch();
        Bundle args = new Bundle();
        args.putString(PITCH_COMPANY, pitchCompany);
        args.putString(PITCH_BLURB, pitchBlurb);
        args.putString(PITCH_CAMPAIGN, pitchCampaignName);
        args.putString(PITCH_SPONSOR, pitchSponsor);
        args.putString(VIDEO_URL, videoURL);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPitchCompany = getArguments().getString(PITCH_COMPANY);
            mPitchBlurb = getArguments().getString(PITCH_BLURB);
            mPitchCampaignName = getArguments().getString(PITCH_CAMPAIGN);
            mPitchSponsor = getArguments().getString(PITCH_SPONSOR);
            mVideoURL = getArguments().getString(VIDEO_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_pitch, container, false);

        Context context = this.getActivity();
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        TextView companyNameTV = (TextView) view.findViewById(R.id.PitchCompanyNameTextView);
        companyNameTV.setText(mPitchCompany);

        TextView campaignName = (TextView) view.findViewById(R.id.PitchCampaignNameTextView);
        campaignName.setText(mPitchCampaignName);

        TextView companyBlurb = (TextView) view.findViewById(R.id.pitchBlurb);
        companyBlurb.setText(mPitchBlurb);

        TextView sponsor = (TextView) view.findViewById(R.id.pitchSponsor);
        sponsor.setText("Sponsored by: " + mPitchSponsor);

        Button applyButton = (Button) view.findViewById(R.id.buttonApply);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e ("Apply_On_Click", "On click event fired");
                if (mListener != null) {
                    mListener.onFragmentInteraction(v);
                } else {
                    Log.e ("Apply_On_Click", "mListener is null");
                }
            }
        });

        youTubeView = (YouTubePlayerView) view.findViewById(R.id.youtubeplayer);
        youTubeView.setVisibility(View.INVISIBLE);
        youTubeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initVideo();
            }
        });

        TextView clickToPlay = (TextView) view.findViewById(R.id.clickToPlay);
        clickToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initVideo();
            }
        });
        return view;
    }

    private void initVideo()
    {
        youTubeView.setVisibility(View.VISIBLE);
        youTubeView.initialize(DEVELOPER_KEY, this);
    }
    private void closeAndHideVideo()
    {
        ytPlayer.release();
        youTubeView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            ytPlayer = player;
            ytPlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                @Override
                public void onLoading() {

                }

                @Override
                public void onLoaded(String s) {

                }

                @Override
                public void onAdStarted() {

                }

                @Override
                public void onVideoStarted() {

                }

                @Override
                public void onVideoEnded() {
                    Log.e("PitchVid", "OnEnded");
                    closeAndHideVideo();
                }

                @Override
                public void onError(YouTubePlayer.ErrorReason errorReason) {
                    Log.e("PitchVid", "OnError: " + errorReason.toString());
                    closeAndHideVideo();
                }
            });
            ytPlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                @Override
                public void onPlaying() {

                }

                @Override
                public void onPaused() {

                }

                @Override
                public void onStopped() {
                    Log.e("PitchVid", "OnStopped");
                    closeAndHideVideo();
                }

                @Override
                public void onBuffering(boolean b) {

                }

                @Override
                public void onSeekTo(int i) {

                }
            });
            ytPlayer.setFullscreen(false);
            ytPlayer.loadVideo(mVideoURL);
            ytPlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("On_Attach", "On attach fired");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(View v);
    }
}
