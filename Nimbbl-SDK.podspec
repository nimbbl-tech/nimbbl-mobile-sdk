Pod::Spec.new do |spec|

  spec.name         = "Nimbbl-SDK"
  spec.version      = "1.0.1"
  spec.summary      = "Nimbbl checkout iOS SDK"
  spec.description  = "Accept payments easily on your app with the Nimbbl iOS SDK.\n\nGet 1-click checkout, higher conversions with multiple buy now pay laters and payment gateways via a single Nimbbl SDK implementation.\n\nNimbbl supports all payment options - Buy Now Pay Later, UPI, Google Pay, Phonepe, Debit/Credit Cards (Visa/ Mastercard/ RuPay/ American express), 50+Netbanking, Wallets and more."
  spec.homepage     = "https://nimbbl.biz"
  spec.license      = "MIT"
  spec.author       = { "Nimbbl" => "support@nimbbl.biz" }
  spec.platform     = :ios, "9.0"
  spec.swift_version = "4.0"
  spec.source       = { :git => "https://gitlab.com/nimbbl/nimbbl_kit_mobile.git", :tag => "#{spec.version}" }
  spec.user_target_xcconfig = { 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'arm64' }
  spec.pod_target_xcconfig = { 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'arm64' }
  spec.ios.vendored_frameworks = 'NimbblCheckoutSDK.framework'
  spec.public_header_files = "NimbblCheckoutSDK.framework/Headers/*.h"
  spec.source_files = 'NimbblCheckoutSDK.framework/Headers/*.h', 'NimbblCheckoutSDK.framework/main.jsbundle'
  #spec.resource = 'NimbblCheckoutSDK.framework/main.jsbundle'
  #spec.ios.resource_bundle = { 'Nimbbl-SDK' => 'NimbblCheckoutSDK.framework/main.jsbundle' }
end