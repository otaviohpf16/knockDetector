package org.prowl.torque.remote;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITorqueService extends IInterface {
    int getConfiguredSpeed() throws RemoteException;

    int getDataErrorCount() throws RemoteException;

    String getDescriptionForPid(long j) throws RemoteException;

    long[] getListOfActivePids() throws RemoteException;

    long[] getListOfAllPids() throws RemoteException;

    long[] getListOfECUSupportedPids() throws RemoteException;

    float getMaxValueForPid(long j) throws RemoteException;

    float getMinValueForPid(long j) throws RemoteException;

    int getNumberOfLoggedItems() throws RemoteException;

    double getPIDReadSpeed() throws RemoteException;

    String getPreferredUnit(String str) throws RemoteException;

    float getScaleForPid(long j) throws RemoteException;

    String getShortNameForPid(long j) throws RemoteException;

    String getUnitForPid(long j) throws RemoteException;

    long getUpdateTimeForPID(long j) throws RemoteException;

    float getValueForPid(long j, boolean z) throws RemoteException;

    String[] getVehicleProfileInformation() throws RemoteException;

    int getVersion() throws RemoteException;

    boolean hasFullPermissions() throws RemoteException;

    boolean isConnectedToECU() throws RemoteException;

    boolean isFileLoggingEnabled() throws RemoteException;

    boolean isWebLoggingEnabled() throws RemoteException;

    String retrieveProfileData(String str) throws RemoteException;

    String[] sendCommandGetResponse(String str, String str2) throws RemoteException;

    boolean sendPIDData(String str, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4, float[] fArr, float[] fArr2, String[] strArr5, String[] strArr6) throws RemoteException;

    boolean setDebugTestMode(boolean z) throws RemoteException;

    boolean setPIDData(String str, String str2, String str3, float f, float f2, float f3) throws RemoteException;

    boolean setPIDInformation(String str, String str2, String str3, float f, float f2, float f3, String str4) throws RemoteException;

    int storeInProfile(String str, String str2, boolean z) throws RemoteException;

    String translate(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ITorqueService {
        private static final String DESCRIPTOR = "org.prowl.torque.remote.ITorqueService";
        static final int TRANSACTION_getConfiguredSpeed = 22;
        static final int TRANSACTION_getDataErrorCount = 20;
        static final int TRANSACTION_getDescriptionForPid = 3;
        static final int TRANSACTION_getListOfActivePids = 8;
        static final int TRANSACTION_getListOfAllPids = 10;
        static final int TRANSACTION_getListOfECUSupportedPids = 9;
        static final int TRANSACTION_getMaxValueForPid = 7;
        static final int TRANSACTION_getMinValueForPid = 6;
        static final int TRANSACTION_getNumberOfLoggedItems = 25;
        static final int TRANSACTION_getPIDReadSpeed = 21;
        static final int TRANSACTION_getPreferredUnit = 13;
        static final int TRANSACTION_getScaleForPid = 27;
        static final int TRANSACTION_getShortNameForPid = 4;
        static final int TRANSACTION_getUnitForPid = 5;
        static final int TRANSACTION_getUpdateTimeForPID = 26;
        static final int TRANSACTION_getValueForPid = 2;
        static final int TRANSACTION_getVehicleProfileInformation = 17;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_hasFullPermissions = 11;
        static final int TRANSACTION_isConnectedToECU = 15;
        static final int TRANSACTION_isFileLoggingEnabled = 23;
        static final int TRANSACTION_isWebLoggingEnabled = 24;
        static final int TRANSACTION_retrieveProfileData = 19;
        static final int TRANSACTION_sendCommandGetResponse = 12;
        static final int TRANSACTION_sendPIDData = 29;
        static final int TRANSACTION_setDebugTestMode = 16;
        static final int TRANSACTION_setPIDData = 14;
        static final int TRANSACTION_setPIDInformation = 30;
        static final int TRANSACTION_storeInProfile = 18;
        static final int TRANSACTION_translate = 28;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITorqueService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITorqueService)) {
                return new Proxy(obj);
            }
            return (ITorqueService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _result = getVersion();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    float _result2 = getValueForPid(data.readLong(), data.readInt() != 0);
                    reply.writeNoException();
                    reply.writeFloat(_result2);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String _result3 = getDescriptionForPid(data.readLong());
                    reply.writeNoException();
                    reply.writeString(_result3);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _result4 = getShortNameForPid(data.readLong());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    String _result5 = getUnitForPid(data.readLong());
                    reply.writeNoException();
                    reply.writeString(_result5);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    float _result6 = getMinValueForPid(data.readLong());
                    reply.writeNoException();
                    reply.writeFloat(_result6);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    float _result7 = getMaxValueForPid(data.readLong());
                    reply.writeNoException();
                    reply.writeFloat(_result7);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    long[] _result8 = getListOfActivePids();
                    reply.writeNoException();
                    reply.writeLongArray(_result8);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    long[] _result9 = getListOfECUSupportedPids();
                    reply.writeNoException();
                    reply.writeLongArray(_result9);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    long[] _result10 = getListOfAllPids();
                    reply.writeNoException();
                    reply.writeLongArray(_result10);
                    return true;
                case TRANSACTION_hasFullPermissions /*11*/:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result11 = hasFullPermissions();
                    reply.writeNoException();
                    reply.writeInt(_result11 ? 1 : 0);
                    return true;
                case TRANSACTION_sendCommandGetResponse /*12*/:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result12 = sendCommandGetResponse(data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeStringArray(_result12);
                    return true;
                case TRANSACTION_getPreferredUnit /*13*/:
                    data.enforceInterface(DESCRIPTOR);
                    String _result13 = getPreferredUnit(data.readString());
                    reply.writeNoException();
                    reply.writeString(_result13);
                    return true;
                case TRANSACTION_setPIDData /*14*/:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result14 = setPIDData(data.readString(), data.readString(), data.readString(), data.readFloat(), data.readFloat(), data.readFloat());
                    reply.writeNoException();
                    reply.writeInt(_result14 ? 1 : 0);
                    return true;
                case TRANSACTION_isConnectedToECU /*15*/:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result15 = isConnectedToECU();
                    reply.writeNoException();
                    reply.writeInt(_result15 ? 1 : 0);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result16 = setDebugTestMode(data.readInt() != 0);
                    reply.writeNoException();
                    reply.writeInt(_result16 ? 1 : 0);
                    return true;
                case TRANSACTION_getVehicleProfileInformation /*17*/:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result17 = getVehicleProfileInformation();
                    reply.writeNoException();
                    reply.writeStringArray(_result17);
                    return true;
                case TRANSACTION_storeInProfile /*18*/:
                    data.enforceInterface(DESCRIPTOR);
                    int _result18 = storeInProfile(data.readString(), data.readString(), data.readInt() != 0);
                    reply.writeNoException();
                    reply.writeInt(_result18);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    String _result19 = retrieveProfileData(data.readString());
                    reply.writeNoException();
                    reply.writeString(_result19);
                    return true;
                case TRANSACTION_getDataErrorCount /*20*/:
                    data.enforceInterface(DESCRIPTOR);
                    int _result20 = getDataErrorCount();
                    reply.writeNoException();
                    reply.writeInt(_result20);
                    return true;
                case TRANSACTION_getPIDReadSpeed /*21*/:
                    data.enforceInterface(DESCRIPTOR);
                    double _result21 = getPIDReadSpeed();
                    reply.writeNoException();
                    reply.writeDouble(_result21);
                    return true;
                case TRANSACTION_getConfiguredSpeed /*22*/:
                    data.enforceInterface(DESCRIPTOR);
                    int _result22 = getConfiguredSpeed();
                    reply.writeNoException();
                    reply.writeInt(_result22);
                    return true;
                case TRANSACTION_isFileLoggingEnabled /*23*/:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result23 = isFileLoggingEnabled();
                    reply.writeNoException();
                    reply.writeInt(_result23 ? 1 : 0);
                    return true;
                case TRANSACTION_isWebLoggingEnabled /*24*/:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result24 = isWebLoggingEnabled();
                    reply.writeNoException();
                    reply.writeInt(_result24 ? 1 : 0);
                    return true;
                case TRANSACTION_getNumberOfLoggedItems /*25*/:
                    data.enforceInterface(DESCRIPTOR);
                    int _result25 = getNumberOfLoggedItems();
                    reply.writeNoException();
                    reply.writeInt(_result25);
                    return true;
                case TRANSACTION_getUpdateTimeForPID /*26*/:
                    data.enforceInterface(DESCRIPTOR);
                    long _result26 = getUpdateTimeForPID(data.readLong());
                    reply.writeNoException();
                    reply.writeLong(_result26);
                    return true;
                case TRANSACTION_getScaleForPid /*27*/:
                    data.enforceInterface(DESCRIPTOR);
                    float _result27 = getScaleForPid(data.readLong());
                    reply.writeNoException();
                    reply.writeFloat(_result27);
                    return true;
                case TRANSACTION_translate /*28*/:
                    data.enforceInterface(DESCRIPTOR);
                    String _result28 = translate(data.readString());
                    reply.writeNoException();
                    reply.writeString(_result28);
                    return true;
                case TRANSACTION_sendPIDData /*29*/:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result29 = sendPIDData(data.readString(), data.createStringArray(), data.createStringArray(), data.createStringArray(), data.createStringArray(), data.createFloatArray(), data.createFloatArray(), data.createStringArray(), data.createStringArray());
                    reply.writeNoException();
                    reply.writeInt(_result29 ? 1 : 0);
                    return true;
                case TRANSACTION_setPIDInformation /*30*/:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result30 = setPIDInformation(data.readString(), data.readString(), data.readString(), data.readFloat(), data.readFloat(), data.readFloat(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result30 ? 1 : 0);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITorqueService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public int getVersion() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public float getValueForPid(long pid, boolean triggersDataRefresh) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(pid);
                    if (triggersDataRefresh) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readFloat();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getDescriptionForPid(long pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(pid);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getShortNameForPid(long pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(pid);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getUnitForPid(long pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(pid);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public float getMinValueForPid(long pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(pid);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readFloat();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public float getMaxValueForPid(long pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(pid);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readFloat();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long[] getListOfActivePids() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createLongArray();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long[] getListOfECUSupportedPids() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createLongArray();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long[] getListOfAllPids() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createLongArray();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasFullPermissions() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_hasFullPermissions, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] sendCommandGetResponse(String header, String command) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(header);
                    _data.writeString(command);
                    this.mRemote.transact(Stub.TRANSACTION_sendCommandGetResponse, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createStringArray();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getPreferredUnit(String unit) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(unit);
                    this.mRemote.transact(Stub.TRANSACTION_getPreferredUnit, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setPIDData(String name, String shortName, String unit, float max, float min, float value) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(shortName);
                    _data.writeString(unit);
                    _data.writeFloat(max);
                    _data.writeFloat(min);
                    _data.writeFloat(value);
                    this.mRemote.transact(Stub.TRANSACTION_setPIDData, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isConnectedToECU() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_isConnectedToECU, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setDebugTestMode(boolean activateTestMode) throws RemoteException {
                int i;
                boolean _result = true;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (activateTestMode) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getVehicleProfileInformation() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getVehicleProfileInformation, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createStringArray();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int storeInProfile(String key, String value, boolean saveToFileNow) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeString(value);
                    if (saveToFileNow) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_storeInProfile, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String retrieveProfileData(String key) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDataErrorCount() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getDataErrorCount, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public double getPIDReadSpeed() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getPIDReadSpeed, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readDouble();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getConfiguredSpeed() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getConfiguredSpeed, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isFileLoggingEnabled() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_isFileLoggingEnabled, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isWebLoggingEnabled() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_isWebLoggingEnabled, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getNumberOfLoggedItems() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getNumberOfLoggedItems, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getUpdateTimeForPID(long pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(pid);
                    this.mRemote.transact(Stub.TRANSACTION_getUpdateTimeForPID, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readLong();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public float getScaleForPid(long pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(pid);
                    this.mRemote.transact(Stub.TRANSACTION_getScaleForPid, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readFloat();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String translate(String originalText) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(originalText);
                    this.mRemote.transact(Stub.TRANSACTION_translate, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean sendPIDData(String pluginName, String[] name, String[] shortName, String[] modeAndPID, String[] equation, float[] minValue, float[] maxValue, String[] units, String[] header) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pluginName);
                    _data.writeStringArray(name);
                    _data.writeStringArray(shortName);
                    _data.writeStringArray(modeAndPID);
                    _data.writeStringArray(equation);
                    _data.writeFloatArray(minValue);
                    _data.writeFloatArray(maxValue);
                    _data.writeStringArray(units);
                    _data.writeStringArray(header);
                    this.mRemote.transact(Stub.TRANSACTION_sendPIDData, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setPIDInformation(String name, String shortName, String unit, float max, float min, float value, String stringValue) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(shortName);
                    _data.writeString(unit);
                    _data.writeFloat(max);
                    _data.writeFloat(min);
                    _data.writeFloat(value);
                    _data.writeString(stringValue);
                    this.mRemote.transact(Stub.TRANSACTION_setPIDInformation, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
