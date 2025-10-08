import { useState, useEffect } from "react";
import api from "../api/axios";
import { useNavigate, Link } from "react-router-dom";

export default function Register() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [fullName, setFullName] = useState("");
    const [roleId, setRoleId] = useState(3);
    const [schoolId, setSchoolId] = useState("");
    const [schools, setSchools] = useState([]);
    const [error, setError] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        fetch("http://localhost:8080/api/schools")
            .then(res => res.json())
            .then(data => setSchools(data));
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!email || !password || !fullName || !schoolId) return setError("All fields are required");
        try {
            await api.post("/register", { email, password, fullName, roleId, schoolId });
            navigate("/login");
        } catch (err) {
            setError(err.response?.data || "Register failed");
        }
    };

    return (
        <div className="flex items-center justify-center h-screen bg-gray-100">
            <div className="w-full max-w-md p-8 bg-white rounded shadow">
                <h2 className="text-2xl font-bold mb-6 text-center">Register</h2>
                {error && <p className="text-red-500 mb-4">{error}</p>}
                <form onSubmit={handleSubmit} className="space-y-4">
                    <input type="text" placeholder="Full Name" value={fullName}
                        onChange={e => setFullName(e.target.value)}
                        className="w-full p-2 border rounded" required />
                    <input type="email" placeholder="Email" value={email}
                        onChange={e => setEmail(e.target.value)}
                        className="w-full p-2 border rounded" required />
                    <input type="password" placeholder="Password" value={password}
                        onChange={e => setPassword(e.target.value)}
                        className="w-full p-2 border rounded" required />
                    <select value={schoolId} onChange={e => setSchoolId(Number(e.target.value))}
                        className="w-full p-2 border rounded" required>
                        <option value="">Select School</option>
                        {schools.map(s => <option key={s.id} value={s.id}>{s.name}</option>)}
                    </select>
                    <select value={roleId} onChange={e => setRoleId(Number(e.target.value))}
                        className="w-full p-2 border rounded" required>
                        <option value={1}>Admin</option>
                        <option value={2}>Teacher</option>
                        <option value={3}>Student</option>
                    </select>
                    <button type="submit" className="w-full bg-green-500 text-white p-2 rounded hover:bg-green-600">Register</button>
                </form>
                <p className="mt-4 text-center text-sm"><Link to="/login" className="text-blue-500 hover:underline">Login</Link></p>
            </div>
        </div>
    );
}
