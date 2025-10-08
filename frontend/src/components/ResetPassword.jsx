import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";

const ResetPassword = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const { email, token } = location.state || {}; // nhận từ ForgotPassword

    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [message, setMessage] = useState("");

    const handleResetPassword = async (e) => {
        e.preventDefault();
        if (newPassword !== confirmPassword) {
            setMessage("Mật khẩu không khớp!");
            return;
        }
        try {
            await axios.post("http://localhost:8080/api/auth/reset-password", {
                email,
                token,
                newPassword,
            });
            setMessage("Đặt lại mật khẩu thành công ✅");
            setTimeout(() => navigate("/login"), 1500);
        } catch (err) {
            setMessage("Đặt lại mật khẩu thất bại!");
        }
    };

    if (!email || !token) {
        return (
            <div className="flex justify-center items-center h-screen">
                <p className="text-red-600">Không có thông tin xác thực. Vui lòng thử lại từ đầu.</p>
            </div>
        );
    }

    return (
        <div className="flex justify-center items-center h-screen bg-gray-100">
            <div className="bg-white p-6 rounded-lg shadow-md w-96">
                <h2 className="text-2xl font-bold mb-4 text-center">Đặt lại mật khẩu</h2>

                <form onSubmit={handleResetPassword}>
                    <input
                        type="password"
                        placeholder="Mật khẩu mới"
                        className="w-full border p-2 mb-3 rounded"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                        required
                    />
                    <input
                        type="password"
                        placeholder="Nhập lại mật khẩu"
                        className="w-full border p-2 mb-3 rounded"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        required
                    />
                    <button
                        type="submit"
                        className="w-full bg-purple-500 text-white py-2 rounded hover:bg-purple-600"
                    >
                        Xác nhận
                    </button>
                </form>

                {message && <p className="mt-4 text-center text-sm text-gray-700">{message}</p>}
            </div>
        </div>
    );
};

export default ResetPassword;
