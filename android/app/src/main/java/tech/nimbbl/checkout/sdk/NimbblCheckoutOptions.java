/*
 * Copyright @ 2019-present 8x8, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.nimbbl.checkout.sdk;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;


/**
 * This class represents the options when opening Nimbbl heckout. The user can create an
 * instance by using {@link NimbblCheckoutOptions.Builder} and setting the desired options
 * there.
 *
 * The resulting {@link NimbblCheckoutOptions} object is immutable and represents how the
 * checkout will be opened.
 */
public class NimbblCheckoutOptions implements Parcelable {
    /**
     * string API Key ID that must generated from the Nimbbl Dashboard.
     */
    private String accessKey;
    /**
     * integer The amount to be paid by the customer in currency.
     */
    private int amount;
    /**
     * string The currency in which the payment should be made by the customer.
     */
    private String currency;
    /**
     * string The merchant/company name shown in the Checkout form..
     */
    private String name;

    /**
     * string Description of the purchase item shown in the Checkout form. Must start with an alphanumeric character.
     */
    private String description;

    /**
     * string Link to an image (usually your business logo) shown in the Checkout form. Can also be a base64 string, if loading the image from a network is not desirable.
     */
    private String image;

    /**
     * string Order ID generated via Orders Api
     */
    private String orderID;


    /**
     * USer information, to be used when no token is specified.
     */
    private NimbblCheckoutUserInfo userInfo;

    public String getKey() {
        return accessKey;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getOrderId() {
        return orderID;
    }

    public NimbblCheckoutUserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * Class used to build the immutable {@link NimbblCheckoutOptions} object.
     */
    public static class Builder {
        private String accessKey;
        private int amount;
        private String currency;
        private String name;
        private String description;
        private String image;
        private String orderID;
        private NimbblCheckoutUserInfo userInfo;

        public Builder() {
            //featureFlags = new Bundle();
        }

        /**\
         * Sets the server URL.
         * @param key - {@link URL} of the server where the conference should take place.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setKey(String key) {
            this.accessKey = key;

            return this;
        }

        /**
         * Sets the room where the conference will take place.
         * @param amount - Name of the room.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setAmount(int amount) {
            this.amount = amount;

            return this;
        }

        /**
         * Sets the conference subject.
         * @param currency - Subject for the conference.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setCurrency(String currency) {
            this.currency = currency;

            return this;
        }

        /**
         * Sets the JWT token to be used for authentication when joining a conference.
         * @param name - The JWT token to be used for authentication.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setName(String name) {
            this.name = name;

            return this;
        }

        /**
         * Sets the color scheme override so the app is themed. See:
         * for the structure.
         * @param description - A color scheme to be applied to the app.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setDescription(String description) {
            this.description = description;

            return this;
        }

        /**
         * Indicates the conference will be joined with the microphone muted.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setImage(String image) {
            this.image = image;

            return this;
        }

        /**
         * Indicates the conference will be joined in audio-only mode. In this mode no video is
         * sent or received.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setOrderId(String orderId) {
            this.orderID = orderId;

            return this;
        }

        public Builder setUserInfo(NimbblCheckoutUserInfo userInfo) {
            this.userInfo = userInfo;

            return this;
        }

        /**
         * Builds the immutable {@link NimbblCheckoutOptions} object with the configuration
         * that this {@link Builder} instance specified.
         * @return - The built {@link NimbblCheckoutOptions} object.
         */
        public NimbblCheckoutOptions build() {
            NimbblCheckoutOptions options = new NimbblCheckoutOptions();

            options.accessKey = this.accessKey;
            options.amount = this.amount;
            options.currency = this.currency;
            options.name = this.name;
            options.description = this.description;
            options.image = this.image;
            options.orderID = this.orderID;
            options.userInfo = this.userInfo;

            return options;
        }
    }

    private NimbblCheckoutOptions() {
    }

    private NimbblCheckoutOptions(Parcel in) {
        accessKey = in.readString();
        amount = in.readInt();
        currency = in.readString();
        name = in.readString();
        description = in.readString();
        image = in.readString();
        orderID = in.readString();
        userInfo = new NimbblCheckoutUserInfo(in.readBundle());
        
    }

    Bundle asProps() {
        Bundle props = new Bundle();

        props.putString("accessKey", accessKey);
        props.putInt("amount", amount);
        props.putString("currency", currency);
        props.putString("name", name);
        props.putString("description", description);
        props.putString("image", image);
        props.putString("orderID", orderID);

        if (userInfo != null) {
            props.putBundle("userInfo", userInfo.asBundle());
        }

        return props;
    }

    // Parcelable interface
    //

    public static final Creator<NimbblCheckoutOptions> CREATOR = new Creator<NimbblCheckoutOptions>() {
        @Override
        public NimbblCheckoutOptions createFromParcel(Parcel in) {
            return new NimbblCheckoutOptions(in);
        }

        @Override
        public NimbblCheckoutOptions[] newArray(int size) {
            return new NimbblCheckoutOptions[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accessKey);
        dest.writeInt(amount);
        dest.writeString(currency);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(orderID);
        dest.writeBundle(userInfo != null ? userInfo.asBundle() : new Bundle());
        
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
