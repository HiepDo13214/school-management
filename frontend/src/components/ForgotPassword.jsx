import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const ForgotPassword = () => {
    const [email, setEmail] = useState("");
    const [token, setToken] = useState("");
    const [step, setStep] = useState(1);
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const handleSendToken = async (e) => {
        e.preventDefault();
        try {
            await axios.post("http://localhost:8080/api/auth/forgot-password", { email });
            setMessage("Token đã được gửi! Kiểm tra console backend.");
            setStep(2);
        } catch (err) {
            setMessage("Email không tồn tại hoặc có lỗi xảy ra.");
        }
    };

    const handleVerifyToken = async (e) => {
        e.preventDefault();
        try {
            await axios.post("http://localhost:8080/api/auth/verify-token", { email, token });
            // ✅ Nếu đúng thì chuyển sang trang ResetPassword và truyền email + token
            navigate("/reset-password", { state: { email, token } });
        } catch (err) {
            setMessage("Token không đúng hoặc đã hết hạn.");
        }
    };

    return (
        <div className="flex justify-center items-center h-screen bg-gray-100">
            <div className="bg-white p-6 rounded-lg shadow-md w-96">
                <h2 className="text-2xl font-bold mb-4 text-center">Quên mật khẩu</h2>

                {step === 1 && (
                    <form onSubmit={handleSendToken}>
                        <input
                            type="email"
                            placeholder="Nhập email"
                            className="w-full border p-2 mb-3 rounded"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                        <button
                            type="submit"
                            className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600"
                        >
                            Gửi token
                        </button>
                    </form>
                )}

                {step === 2 && (
                    <form onSubmit={handleVerifyToken}>
                        <input
                            type="text"
                            placeholder="Nhập token"
                            className="w-full border p-2 mb-3 rounded"
                            value={token}
                            onChange={(e) => setToken(e.target.value)}
                            required
                        />
                        <button
                            type="submit"
                            className="w-full bg-green-500 text-white py-2 rounded hover:bg-green-600"
                        >
                            Xác thực token
                        </button>
                    </form>
                )}

                {message && <p className="mt-4 text-center text-sm text-gray-700">{message}</p>}
            </div>
        </div>
    );
};

export default ForgotPassword;
