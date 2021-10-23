import { useState } from 'react';
import { Button } from 'antd';
import { ArrowLeftOutlined } from '@ant-design/icons';
import { toast } from 'react-toastify';
import { exportTrash } from '../api/trash';

export const withSubmit = Component => props => {
    const [values, setValues] = useState(null);

    const onChange = values => setValues(values);

    const onSubmit = async event => {
        event.preventDefault();
        try {
          await exportTrash(values);
          toast.success('Інформація успішно збережена');
        } catch (e) {
          toast.error('Відбулася невідома помилка');
        }
    };

    return (
        <div>
            <ArrowLeftOutlined onClick={() => props.goBack()} />
            <form onSubmit={onSubmit}>
                <Component {...props} onChange={onChange} />
                <Button htmlType='submit'>Зберегти</Button>
            </form>
        </div>
    );
};
