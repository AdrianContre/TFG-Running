import { useState } from "react";
import React from 'react'
import '../styles/Register.css'
import Button from 'react-bootstrap/Button';
import { useNavigate } from 'react-router';
import { registerService } from "../services/authService";
import PopUp from './PopUp'
import { getUserLogged } from "../../home/services/mainService";



function Register () {

    const navigate = useNavigate()

    const [username, setUsername] = useState("")
    const [name, setName] = useState("")
    const [surname, setSurname] = useState("")
    const [mail, setMail] = useState("")
    const [password, setPassword] = useState("")
    const [isTrainer, setIsTrainer] = useState(false)
    const [error, setError] = useState("")
    const [show,setShow] = useState(false)
    const [isTermsAccepted, setIsTermsAccepted] = useState(false);
    const [consent, setConsent] = useState(false)

    const updateUsername = (event) => {
        setUsername(event.target.value)
    }
    
    const updatePwd = (event) => {
        setPassword(event.target.value)
    }
    const updateName= (event) => {
        setName(event.target.value)
    }
    
    const updateSurname = (event) => {
        setSurname(event.target.value)
    }

    const updateMail = (event) => {
        setMail(event.target.value)
    }

    const handleSelectChange = (event) => {
        setIsTrainer(event.target.value);
    };
    
    const handleCheckboxChange = (event) => {
        setIsTermsAccepted(event.target.checked);
    };

    const handleConsentChange = (event) => {
        setConsent(event.target.checked);
    };
    

    const handleClick = async (event) => {
        event.preventDefault()
        console.log(isTrainer)
        if (name == "" || surname == "" || mail == "" || username == "" || password == "" || isTrainer === null) {
            setShow(true)
            setError("Todos los campos han de estar completos")
        }
        else {
            try {
                const data = await registerService(name, surname, mail, username, password, isTrainer, isTermsAccepted);
                if (data.token) {
                    localStorage.setItem('token', data.token)
                    console.log(data.token)
                    console.log('Registration successful:', data);

                    const user = await getUserLogged();
                    localStorage.setItem('userAuth', JSON.stringify(user))
                    navigate('/activities')
                }
                else {
                    if (data.error) {
                        setError(data.error)
                        setUsername("")
                        setName("")
                        setSurname("")
                        setMail("")
                        setPassword("")
                        setIsTrainer(false)
                        setShow(true)
                        
                    }
                }
                
            } catch (error) {
                console.log(error)
            }
        }
        
    }

    const handleAlreadyAnAccount = (event) => {
        event.preventDefault()
        navigate('/login')
    }

    const onHide = (event) => {
        event.preventDefault()
        setShow(false)
    }
    return (
        <>
            <p className='title-sec-register'>RUNNING2ALL</p>
            <div className='d-flex align-items-center justify-content-center'> 
                <div className='container-register d-grid d-flex align-items-center' >
                    <label className='custom-label-register' htmlFor="name">NOMBRE</label>
                    <input name='name' value={name} className='custom-input-register' onChange={updateName}></input>
                    <label className='custom-label-register' htmlFor="surname">APELLIDOS</label>
                    <input name='surname' value={surname} className='custom-input-register' onChange={updateSurname}></input>
                    <label className='custom-label-register' htmlFor="username">NOMBRE DE USUARIO</label>
                    <input name='username' value={username} className='custom-input-register' onChange={updateUsername}/>
                    <label className='custom-label-register' htmlFor="password">CONTRASEÑA</label>
                    <input name='password' type={"password"} value={password} className='custom-input-register' onChange={updatePwd}></input>
                    <label className='custom-label-register' htmlFor="email">EMAIL</label>
                    <input name='email'  value={mail} className='custom-input-register' onChange={updateMail}></input>
                    <label className='custom-label-register' htmlFor="type">TIPO DE USUARIO</label>
                    <select name="user_type" className="custom-input-register" value={isTrainer} onChange={handleSelectChange}>
                        <option value="false" selected>Corredor</option>
                        <option value="true">Entrenador</option>
                    </select>
                    <label>
                    <input className="checkbox" type="checkbox" onChange={handleConsentChange}/>
                    Declaro que soy mayor de edad o cuento con el consentimiento de mi representante legal para registrarme en esta plataforma.
                    </label>
                    <label>
                    <input className="checkbox" type="checkbox" id="termsCheckbox" onChange={handleCheckboxChange}/>
                    He leído y acepto los <a className="custom-label-register" style={{ marginTop: '10px', cursor: 'pointer'}}  onClick={() => {navigate('/terms')}}>Términos y Condiciones</a> y la <a className="custom-label-register" style={{ marginTop: '10px', cursor: 'pointer'}}  onClick={() => {navigate('/privacy')}}>Política de Privacidad</a>.
                    </label>
                    <a className="custom-label-register" style={{fontSize: '20px', marginTop: '10px', cursor: 'pointer'}} onClick={handleAlreadyAnAccount}>Ya tienes cuenta ?</a>
                    <Button id="register-button" variant='primary' size='lg' className='mt-5 custom-button-register' onClick={handleClick} disabled={!isTermsAccepted || !consent}>REGISTRATE</Button>
                    
                </div>
            </div>
            <PopUp error={error} show={show} onHide={onHide} title={"Error al registrarse"}/>
        </>
    )
}

export default Register;