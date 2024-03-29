package org.embulk.input.error_in_cleanup;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import org.embulk.config.Config;
import org.embulk.config.ConfigDefault;
import org.embulk.config.ConfigDiff;
import org.embulk.config.ConfigInject;
import org.embulk.config.ConfigSource;
import org.embulk.config.Task;
import org.embulk.config.TaskReport;
import org.embulk.config.TaskSource;
import org.embulk.spi.Buffer;
import org.embulk.spi.BufferAllocator;
import org.embulk.spi.Exec;
import org.embulk.spi.FileInputPlugin;
import org.embulk.spi.TransactionalFileInput;
import org.embulk.spi.util.InputStreamTransactionalFileInput;

public class ErrorInCleanupFileInputPlugin
        implements FileInputPlugin
{
    public interface PluginTask
            extends Task
    {
        // configuration option 1 (required integer)
        @Config("option1")
        public int getOption1();

        // configuration option 2 (optional string, null is not allowed)
        @Config("option2")
        @ConfigDefault("\"myvalue\"")
        public String getOption2();

        // configuration option 3 (optional string, null is allowed)
        @Config("option3")
        @ConfigDefault("null")
        public Optional<String> getOption3();

        //@Config("path_prefix")
        //public String getPathPrefix();

        //@Config("last_path")
        //@ConfigDefault("null")
        //public Optional<String> getLastPath();

        // usually, you store list of files in task to pass them from transaction() to run().
        //public List<String> getFiles();
        //public void setFiles(List<String> files);

        @ConfigInject
        public BufferAllocator getBufferAllocator();
    }

    @Override
    public ConfigDiff transaction(ConfigSource config, FileInputPlugin.Control control)
    {
        PluginTask task = config.loadConfig(PluginTask.class);

        // run() method is called for this number of times in parallel.
        int taskCount = 1;

        // usually, taskCount is number of input files.
        //task.setFiles(listFiles(task));
        //int taskCount = task.getFiles().size();

        return resume(task.dump(), taskCount, control);
    }

    // usually, you have an method to create list of files
    //List<String> listFiles(PluginTask task)
    //{
    //    final ImmutableList.Builder<String> builder = ImmutableList.builder();
    //    for (String path : listFilesWithPrefix(task.getPathPrefix())) {
    //        if (task.getLastPath().isPresent() && path.compareTo(task.getLastPath().get())) {
    //            continue;
    //        }
    //        builder.add(path);
    //    }
    //    return builder.build();
    //}

    @Override
    public ConfigDiff resume(TaskSource taskSource,
            int taskCount,
            FileInputPlugin.Control control)
    {
        control.run(taskSource, taskCount);

        ConfigDiff configDiff = Exec.newConfigDiff();

        // usually, yo uset last_path
        //if (task.getFiles().isEmpty()) {
        //    if (task.getLastPath().isPresent()) {
        //        configDiff.set("last_path", task.getLastPath().get());
        //    }
        //} else {
        //    List<String> files = new ArrayList<String>(task.getFiles());
        //    Collections.sort(files);
        //    configDiff.set("last_path", files.get(files.size() - 1));
        //}

        return configDiff;
    }

    @Override
    public void cleanup(TaskSource taskSource,
            int taskCount,
            List<TaskReport> successTaskReports)
    {
        taskSource.loadTask(PluginTask.class);
    }

    @Override
    public TransactionalFileInput open(TaskSource taskSource, int taskIndex)
    {
        final PluginTask task = taskSource.loadTask(PluginTask.class);

        return new TransactionalFileInput() {
            @Override
            public Buffer poll()
            {
                return null;
            }

            @Override
            public boolean nextFile()
            {
                return false;
            }

            @Override
            public void close()
            {

            }

            @Override
            public void abort()
            {

            }

            @Override
            public TaskReport commit()
            {
                return null;
            }
        };
    }

    //private static InputStream openInputStream(PluginTask task, String path)
    //{
    //    return new MyInputStream(file);
    //}
}
