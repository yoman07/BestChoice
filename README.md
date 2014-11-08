BestChoice
==========

AB tests framework for Android.

Example
=======

Firstly you have to call setup function:
        BestChoiceAB.setup(context, new MixPanelClient(context)); //MixPanelClient is client where you collect data about test which implements BestChoiceABClient
Here's how you might use it for simple A/B testing:

        BestChoiceAB.test("firstScreenLogo", new BestChoiceABTest() {
            public void A() {
                // Display new old logo
            }

            public void B() {
                // Display old logo
            }
        });


        

Example client for MixPanel
------------
        
        public class MixPanelClient implements BestChoiceABClient {
     
     Context context;
    private String TAG = MixPanelClient.class.getName();

    public MixPanelClient(Context context) {
        this.context = context;
    }

    @Override
    public void sendChoicedTest(String name, String value) {

        MixpanelAPI mixpanel =
                MixpanelAPI.getInstance(context, context.getResources().getString(com.zdobywacz.R.string.MIXPANEL_TOKEN));

        JSONObject props = new JSONObject();
        try {
            props.put(name, value);
            mixpanel.registerSuperPropertiesOnce(props);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
  }

