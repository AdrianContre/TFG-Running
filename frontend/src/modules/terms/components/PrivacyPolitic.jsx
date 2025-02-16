import { useNavigate } from 'react-router';
import '../styles/privacy.css'

function PrivacyPolitic() {
    const navigate = useNavigate()
    const navigateToTerms = (event) => {
        event.preventDefault()
        navigate('/terms')
    }
    return (
        <>
            <div className="terms-main-container">
                <h1>Política de Privacidad de RUNNING2ALL:</h1>
                <h6>Fecha de entrada en vigor: 12 de Diciembre de 2024</h6>
                <hr></hr>
                <div className='list-container'>
                    <section>
                        <p>Su privacidad es muy importante para nosotros. Antes de llegar a los detalles, aquí hay un resumen de algunas de estas prácticas fundamentales sobre privacidad.</p>
                    </section>
                    <ol>
                        <h3><li>Introducción</li></h3>
                        <p>Bienvenido/a a la Política de Privacidad de <strong>RUNNING2ALL</strong>. En este documento, explicamos cómo recopilamos, utilizamos, protegemos y compartimos sus datos personales cuando utiliza nuestra plataforma.</p>
                        <p>Nuestro compromiso es garantizar que su información sea tratada con el máximo respeto, en cumplimiento de las normativas de privacidad aplicables, como el Reglamento General de Protección de Datos (GDPR).</p>
                        <p>Por favor, lea también nuestros <a className='custom-label-register' style={{fontSize: '16px', marginTop: '10px', cursor:'pointer'}} onClick={navigateToTerms}>Términos y Condiciones</a>, donde encontrará información sobre las reglas que rigen el uso de nuestra plataforma.</p>
                        <span>Esta política describe:</span>
                        <ul>
                            <strong><li>Que datos personales recopilamos y por qué.</li></strong>
                            <strong><li>Cómo protegemos y gestionamos su información.</li></strong>
                            <strong><li>Qué derechos tiene sobre sus datos y cómo ejercerlos.</li></strong>
                        </ul>
                        <br></br>
                        <p>Si tiene alguna duda sobre está política, no dude en ponerse en contacto con nosotros.</p>

                        <h3><li>Datos recogidos</li></h3>
                        <ul>
                            <li>Los datos recogidos por la aplicación son los siguientes:
                                <ul>
                                    <li>Nombre</li>
                                    <li>Apellidos</li>
                                    <li>Correo electrónico</li>
                                    <li>Peso</li>
                                    <li>Altura</li>
                                    <li>Frecuencia cardiaca máxima</li>
                                </ul>
                            </li>
                            <li>Cuando desee puede eliminar los datos recogidos y se borrarán de nuestro sistema.</li>
                        </ul>

                        <h3><li>Finalidad del tratamiento</li></h3>
                        <ul>
                            <li>Creación de un perfil para poder gestionar sus entrenamientos.</li>
                            <li>Personalización de métricas.</li>
                            <li>Comunicaciones relacionadas con la plataforma, como notificaciones, cambios en los términos, etc.</li>
                        </ul>

                        <h3><li>Base Legal</li></h3>
                        <ul>
                            <li>El tratamiento se basa en el consentimiento explícito del usuario, otorgado en el momento del registro.</li>
                        </ul>

                        <h3><li>Menores de edad</li></h3>
                        <ul>
                            <li>Los datos de menores de edad solo serán tratados mediante el consentimiento explícito de su representante legal en el registro de la cuenta.</li>
                        </ul>

                        <h3><li>Confidencialidad y seguridad</li></h3>
                        <ul>
                            <li>Para la seguridad de sus datos y de accesos indeseados a su cuenta, se mantiene la contraseña encriptada.</li>
                        </ul>

                        <h3><li>Conservación de los datos</li></h3>
                        <ul>
                            <li>Se conservarán sus datos mientras tenga una cuenta activa.</li>
                        </ul>

                        <h3><li>Derechos de usuario</li></h3>
                        <ul>
                            <li>Acceso: Puede ver sus datos cuando desee.</li>
                            <li>Rectificación: Puede corregir sus datos en cualquier momento.</li>
                            <li>Supresión: Puede eliminar su cuenta y datos en cualquier momento.</li>
                            <li>Oposición: Puede retirar el consentimiento en cualquier momento eliminando su perfil.</li>
                        </ul>

                        <h3><li>Compartición de datos</li></h3>
                        <ul>
                            <li>No compartimos su información con ningún servicio de tercero.</li>
                        </ul>

                        <h3><li>Contacto</li></h3>
                        <ul>
                            <li><strong>Correo electrónico: </strong><a className='custom-label-register' style={{fontSize: '16px', marginTop: '10px'}}>running2all@gmail.com</a></li>
                        </ul>
                    </ol>
                </div>
            </div>
        </>
    )
}

export default PrivacyPolitic;