package com.jgkj.grb.utils;
/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

import android.util.Log;

import com.jgkj.grb.BuildConfig;

/**
 * 1.v(verbose)：任何信息都会输出
 * 2.d(debug)：输出调试信息
 * 3.w(warning)：输出警告信息
 * 4.i(info)：输出提示信息
 * 5.e(error)：输出错误信息
 */
public class Logger {
    private static final String LOGGER = "Logger";
    private static final int MAX_LENGTH = 2000; // MAX：4*1024

    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            int length = msg.length();
            if (length > MAX_LENGTH) {
                for (int i = 0; i < length; i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < length) {
                        Log.v(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, i + MAX_LENGTH));
                    } else {
                        Log.v(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, length));
                    }
                }
            } else {
                Log.v(String.format("%1$s_%2$s", LOGGER, tag), msg);
            }
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            int length = msg.length();
            if (length > MAX_LENGTH) {
                for (int i = 0; i < length; i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < length) {
                        Log.v(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, i + MAX_LENGTH), tr);
                    } else {
                        Log.v(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, length), tr);
                    }
                }
            } else {
                Log.v(String.format("%1$s_%2$s", LOGGER, tag), msg, tr);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            int length = msg.length();
            if (length > MAX_LENGTH) {
                for (int i = 0; i < length; i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < length) {
                        Log.d(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, i + MAX_LENGTH));
                    } else {
                        Log.d(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, length));
                    }
                }
            } else {
                Log.d(String.format("%1$s_%2$s", LOGGER, tag), msg);
            }
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            int length = msg.length();
            if (length > MAX_LENGTH) {
                for (int i = 0; i < length; i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < length) {
                        Log.d(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, i + MAX_LENGTH), tr);
                    } else {
                        Log.d(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, length), tr);
                    }
                }
            } else {
                Log.d(String.format("%1$s_%2$s", LOGGER, tag), msg, tr);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            int length = msg.length();
            if (length > MAX_LENGTH) {
                for (int i = 0; i < length; i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < length) {
                        Log.i(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, i + MAX_LENGTH));
                    } else {
                        Log.i(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, length));
                    }
                }
            } else {
                Log.i(String.format("%1$s_%2$s", LOGGER, tag), msg);
            }
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            int length = msg.length();
            if (length > MAX_LENGTH) {
                for (int i = 0; i < length; i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < length) {
                        Log.i(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, i + MAX_LENGTH), tr);
                    } else {
                        Log.i(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, length), tr);
                    }
                }
            } else {
                Log.i(String.format("%1$s_%2$s", LOGGER, tag), msg, tr);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            int length = msg.length();
            if (length > MAX_LENGTH) {
                for (int i = 0; i < length; i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < length) {
                        Log.w(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, i + MAX_LENGTH));
                    } else {
                        Log.w(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, length));
                    }
                }
            } else {
                Log.w(String.format("%1$s_%2$s", LOGGER, tag), msg);
            }
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            int length = msg.length();
            if (length > MAX_LENGTH) {
                for (int i = 0; i < length; i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < length) {
                        Log.w(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, i + MAX_LENGTH), tr);
                    } else {
                        Log.w(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, length), tr);
                    }
                }
            } else {
                Log.w(String.format("%1$s_%2$s", LOGGER, tag), msg, tr);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            int length = msg.length();
            if (length > MAX_LENGTH) {
                for (int i = 0; i < length; i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < length) {
                        Log.e(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, i + MAX_LENGTH));
                    } else {
                        Log.e(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, length));
                    }
                }
            } else {
                Log.e(String.format("%1$s_%2$s", LOGGER, tag), msg);
            }
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            int length = msg.length();
            if (length > MAX_LENGTH) {
                for (int i = 0; i < length; i += MAX_LENGTH) {
                    if (i + MAX_LENGTH < length) {
                        Log.e(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, i + MAX_LENGTH), tr);
                    } else {
                        Log.e(String.format("%1$s_%2$s", LOGGER, tag), msg.substring(i, length), tr);
                    }
                }
            } else {
                Log.e(String.format("%1$s_%2$s", LOGGER, tag), msg, tr);
            }
        }
    }
}
