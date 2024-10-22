import React from 'react'
import background from '../../../assets/images/background.png'
import '../styles/Login.css'
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import { useState } from 'react';
import {loginService} from '../services/authService'
import { useNavigate } from 'react-router';
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
// import {faEyeSlash} from '@fortawesome/free-solid-svg-icons'

function Login() {

    const navigate = useNavigate()

    const handleClick = async (event) => {
        event.preventDefault()
        try {
            const data = await loginService(username, password);
            if (data.token) {
                localStorage.setItem('token', data.token)
                console.log(data.token)
            }
            console.log('Login successful:', data);
            navigate('/main')
        } catch (error) {
            console.log(error)
        }
    }
    
    const updateUsername = (event) => {
        setUsername(event.target.value)
    }
    
    const updatePwd = (event) => {
        setPassword(event.target.value)
    }

    const [username,setUsername] = useState('')
    const [password, setPassword] = useState('')
    return (
        <>
            <div className='d-flex align-items-center justify-content-center' style={{marginTop: '60px'}}>
                <p className='title-sec-login'>RUNNING2ALL</p>
                <div className='button-container d-grid d-flex align-items-center'>
                    <label className='custom-label-login' htmlFor="email">USERNAME</label>
                    <input name='username' value={username} className='custom-input-login' onChange={updateUsername}/>
                    <label className='custom-label-login' htmlFor="password">CONTRASEÑA</label>
                    <input name='password' type={"password"} value={password} className='custom-input-login' onChange={updatePwd}></input>
                    <Button variant='primary' size='lg' className='mt-5 custom-button-login' onClick={handleClick}>INICIAR SESIÓN</Button>
                    
                </div>
            </div>
            
        </>
    )
}

export default Login

