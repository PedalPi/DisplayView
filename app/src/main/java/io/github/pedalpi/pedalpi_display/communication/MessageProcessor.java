package io.github.pedalpi.pedalpi_display.communication;

public class MessageProcessor {
    //https://github.com/SalomonBrys/Kotson#accessing-object-fields-via-property-delegates
}

/*
public class MessageProcessor {

    public static Patch process(RequestMessage message, Patch patch) throws JSONException {

        if (message.getType() == ResponseVerb.PATCH) {
            patch = new Patch(message.getContent());

        } else if(message.getType() == ResponseVerb.EFFECT) {
            int indexEffect = message.getContent().getInt("index");
            patch.getEffects().get(indexEffect).toggleStatus();

        } else if(message.getType() == ResponseVerb.PARAM) {
            JSONObject data = message.getContent();

            int effectIndex = data.getInt("effect");
            int paramIndex = data.getInt("param");
            Double value = Parameter.prepareDoubleValue(data, "value");

            Parameter param = patch.getEffects().get(effectIndex).getParameters().get(paramIndex);
            param.setValue(value);
        }

        return patch;
    }

    public static RequestMessage generateEffectStatusToggled(Effect effect) {
        try {
            JSONObject indexNumber = new JSONObject();
            indexNumber.put("index",effect.getIndex());

            return new RequestMessage(ResponseVerb.EFFECT, indexNumber);
        } catch (JSONException e) {
            return new RequestMessage(ResponseVerb.ERROR);
        }
    }

    public static RequestMessage generateUpdateParamValue(Effect effect, Parameter parameter){
        try {
            JSONObject data = new JSONObject();
            data.put("effect", effect.getIndex());
            data.put("param", parameter.getIndex());
            data.put("value", parameter.getValue());

            return new RequestMessage(ResponseVerb.PARAM, data);
        } catch (JSONException e) {
            return new RequestMessage(ResponseVerb.ERROR);
        }
    }
}
*/