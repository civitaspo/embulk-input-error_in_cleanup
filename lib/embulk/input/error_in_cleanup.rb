Embulk::JavaPlugin.register_input(
  "error_in_cleanup", "org.embulk.input.error_in_cleanup.ErrorInCleanupFileInputPlugin",
  File.expand_path('../../../../classpath', __FILE__))
