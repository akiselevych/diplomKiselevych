
import s from "../RegisterConfirmModal/RegisterConfirmModal.module.scss";
import styles from "../VerifyModal/styles.module.scss";
import {Modal} from "@mui/material";
import {useState} from "react";
import {useForm} from "react-hook-form";
import {useDispatch} from "react-redux";
import {reportFundraising} from "../../../store/slices/Volunteer.slice.jsx";

const FundraisingReportModal = ({open, onClose,id}) => {
    const [file, setFile] = useState();

    const {register,handleSubmit, formState: {isValid}} = useForm()

    const dispatch = useDispatch();

    const onSubmit = (data) => {

        const dto = {
            textContent: data.textContent,
            fundraisingId: id
        }

        const fd = new FormData()
        file && fd.append('file', file)
        fd.append('dto', new Blob([JSON.stringify(dto)], { type: 'application/json' }))

        dispatch(reportFundraising({data: fd, id: id}));
        onClose();
    }

    return (
        <Modal open={open} onClose={onClose} className={s.modalInner}
               disableAutoFocus>
            <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
                <h1 className={styles.title}>Завантажте звіт про збір</h1>

                <div className={`${styles.fields} ${styles.column}`}>
                    <div className={styles.inputField}>
                        <label htmlFor="textContent">Текст</label>
                        <textarea cols={4} rows={12} {...register('textContent', {required: true})}/>
                    </div>
                    <div className={styles.inputField}>
                        <label htmlFor="">Файл</label>
                        <input type="file"  placeholder={'Завантажити файл'}
                               onChange={(event) => {
                                   if (event.target.files != null) {
                                       setFile(event.target.files[0])
                                   }
                               }}/>
                    </div>
                </div>
                <div className={styles.btns}>
                    <button type={"submit"} className={styles.submitBtn} disabled={!isValid || !file}>Завантажити
                    </button>
                    <button type={"button"} className={styles.submitBtn} onClick={onClose}>Скасувати</button>
                </div>

            </form>
        </Modal>
    );
};

export default FundraisingReportModal;
