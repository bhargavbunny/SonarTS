/*
 * SonarTS
 * Copyright (C) 2017-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plugin.typescript;

import java.io.IOException;
import org.sonar.api.Startable;
import org.sonar.api.batch.InstantiationStrategy;
import org.sonar.api.batch.ScannerSide;
import org.sonar.api.config.Configuration;
import org.sonar.api.utils.TempFolder;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.plugin.typescript.executable.ExecutableBundle;
import org.sonar.plugin.typescript.executable.ExecutableBundleFactory;
import org.sonarsource.api.sonarlint.SonarLintSide;

@InstantiationStrategy(InstantiationStrategy.PER_BATCH)
@ScannerSide
@SonarLintSide
public class SonarTSServer implements Startable {

  private static final Logger LOG = Loggers.get(ExternalTypescriptSensor.class);

  private Configuration configuration;
  private TempFolder tempFolder;
  private ExecutableBundleFactory bundleFactory;

  public SonarTSServer(Configuration configuration, TempFolder tempFolder, ExecutableBundleFactory bundleFactory) {
    this.configuration = configuration;
    this.tempFolder = tempFolder;
    this.bundleFactory = bundleFactory;
  }

  @Override
  public void start() {
    if (true) {
      throw new RuntimeException("BOOOOOM");
    }
    final ExecutableBundle bundle = bundleFactory.createAndDeploy(tempFolder.newDir("sonarts"), configuration);
    ProcessBuilder processBuilder = new ProcessBuilder(bundle.getSonarTSServerCommand());
    // TODO consider adding NODE_PATH
    try {
      processBuilder.start();
    } catch (IOException e) {
      LOG.error("Failed to start SonarTS Server", e);
    }
  }

  @Override
  public void stop() {

  }
}
