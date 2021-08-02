using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Image.Filter.Android.RNImageFilterAndroid
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNImageFilterAndroidModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNImageFilterAndroidModule"/>.
        /// </summary>
        internal RNImageFilterAndroidModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNImageFilterAndroid";
            }
        }
    }
}
