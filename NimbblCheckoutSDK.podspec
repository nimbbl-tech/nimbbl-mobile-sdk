Pod::Spec.new do |spec|

  spec.name         = "NimbblCheckoutSDK"
  spec.version      = "1.0.1"
  spec.summary      = "NimbblCheckoutSDK is payment gateway sdk"
  spec.description  = "This is a payment gateway framework from nimbbl"
  spec.homepage     = "https://nimbbl.biz"
  spec.license      = "MIT"
  spec.author       = { "Stany Dsouza" => "stany@logicloop.io" }
  spec.platform     = :ios, "9.0"
  spec.swift_version = "4.0"
  spec.source       = { :git => "https://gitlab.com/nimbbl/nimbbl_kit_mobile.git", :tag => "#{spec.version}" }
  spec.vendored_frameworks = "NimbblCheckoutSDK.framework"
  spec.public_header_files = "NimbblCheckoutSDK.framework/Headers/*.h"
  spec.source_files = "NimbblCheckoutSDK.framework/Headers/*.h"

end