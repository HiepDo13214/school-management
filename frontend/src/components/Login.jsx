import { useState } from "react";
import api from "../api/axios";
import { useNavigate, Link } from "react-router-dom";

export default function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(""); // reset lỗi mỗi lần nhấn login

        if (!email || !password) {
            setError("Vui lòng nhập đầy đủ Email và Mật khẩu");
            return;
        }

        try {
            const res = await api.post("/login", { email, password });
            // ✅ Lưu token sau khi đăng nhập thành công
            localStorage.setItem("token", res.data.token);

            // ✅ Chuyển hướng sang dashboard
            navigate("/dashboard");
        } catch (err) {
            // Nếu backend trả lỗi (401, 403), lấy thông điệp từ response
            if (err.response) {
                setError(err.response.data.message || "Email hoặc mật khẩu không đúng");
            } else {
                setError("Không thể kết nối tới server");
            }
        }
    };

    return (
        <div className="flex items-center justify-center h-screen bg-gray-100">
            <div className="w-full max-w-md p-8 bg-white rounded shadow">
                <h2 className="text-2xl font-bold mb-6 text-center">Đăng nhập</h2>

                {/* ✅ Thông báo lỗi */}
                {error && <p className="text-red-500 mb-4 text-center">{error}</p>}

                <form onSubmit={handleSubmit} className="space-y-4">
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="w-full p-2 border rounded"
                        required
                    />
                    <input
                        type="password"
                        placeholder="Mật khẩu"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="w-full p-2 border rounded"
                        required
                    />
                    <button
                        type="submit"
                        className="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600"
                    >
                        Đăng nhập
                    </button>
                </form>

                <div className="mt-4 flex justify-between text-sm">
                    <Link to="/forgot-password" className="text-blue-500 hover:underline">
                        Quên mật khẩu?
                    </Link>
                    <Link to="/register" className="text-blue-500 hover:underline">
                        Đăng ký
                    </Link>
                </div>
            </div>
        </div>
    );
}
