////////////////////////////////////////////////////////////////////////////////
// Copyright 2014 Michael Schmalle - Teoti Graphix, LLC
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
// http://www.apache.org/licenses/LICENSE-2.0 
// 
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, 
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and 
// limitations under the License
// 
// Author: Michael Schmalle, Principal Architect
// mschmalle at teotigraphix dot com
////////////////////////////////////////////////////////////////////////////////

package com.teotigraphix.gdx.app;

import com.badlogic.gdx.Gdx;
import com.google.common.eventbus.EventBus;

/**
 * @author Michael Schmalle
 * @since 1.0
 */
public abstract class Application implements IApplication {

    private static final String TAG = "Application";

    //--------------------------------------------------------------------------
    // Private :: Variables
    //--------------------------------------------------------------------------

    private String applicationName;

    private int width;

    private int height;

    private EventBus eventBus;

    private SceneManager sceneManager;

    //--------------------------------------------------------------------------
    // IApplication API :: Properties
    //--------------------------------------------------------------------------

    @Override
    public String getApplicationName() {
        return applicationName;
    }

    @Override
    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public final EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public IScene getScene() {
        return getSceneManager().getScene();
    }

    @Override
    public void setScene(int sceneId) {
        getSceneManager().setScene(sceneId);
    }

    @Override
    public boolean isCurrentScene(int sceneId) {
        return getSceneManager().isCurrentScene(sceneId);
    }

    //--------------------------------------------------------------------------
    // Protected :: Properties
    //--------------------------------------------------------------------------

    protected final SceneManager getSceneManager() {
        return sceneManager;
    }

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    /**
     * Creates a new {@link Application} specific to the platform
     * {@link ISoundGenerator}.
     * 
     * @param applicationName The name of the application.
     * @param soundGenerator The platform specific {@link ISoundGenerator}.
     */
    public Application(String applicationName) {
        this.applicationName = applicationName;
        eventBus = new EventBus("application");
        sceneManager = new SceneManager(this);
    }

    //--------------------------------------------------------------------------
    // IApplication API :: Methods
    //--------------------------------------------------------------------------

    @Override
    public void create() {
        Gdx.app.log(TAG, "create()");
        //try {
        //Gdx.app.log("StartupExecutor", "create()");
        //runtime = startupExecutor.create(this);
        //getLogger().log("Rack", "initialize()");
        //runtime.getRack().initialize();
        //getLogger().log("Rack", "onStart()");
        //runtime.getRack().onStart();
        //getLogger().log("SceneManager", "create()");
        sceneManager.create();
        //} catch (IOException e) {
        //    // TODO Auto-generated catch block
        //    e.printStackTrace();
        //}
        onRegisterScenes();
        onRegisterModels();
        onCreate();
    }

    @Override
    public void render() {
        sceneManager.preRender();
        // XXX
        sceneManager.postRender();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize(" + width + ", " + height + ")");
        if (sceneManager != null)
            sceneManager.resize(width, height);
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "pause()");
        sceneManager.pause();
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "resume()");
        sceneManager.resume();
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose()");
        sceneManager.dispose();
        //runtime.getRack().onDestroy();
    }

    //--------------------------------------------------------------------------
    // Protected :: Methods
    //--------------------------------------------------------------------------

    /**
     * Register application {@link IModel}s.
     * <p>
     * First of the register methods to be called.
     * 
     * @see ApplicationComponentRegistery#put(Class, IModel)
     */
    protected abstract void onRegisterModels();

    /**
     * Add {@link IScene}s to the application.
     * 
     * @see #onRegisterModels()
     * @see SceneManager#addScene(int, Class)
     */
    protected abstract void onRegisterScenes();

    /**
     * Set the initial {@link Scene} that starts the application, and perform
     * any other various setup tasks before the main user interface is shown.
     * 
     * @see #onRegisterScenes()
     */
    protected abstract void onCreate();

    /**
     * Called by the {@link SceneManager} during a scene change.
     * 
     * @param scene The active scene.
     */
    public void onSceneChange(IScene scene) {
    }
}
