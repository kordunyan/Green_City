import { useEffect, useMemo, useState } from 'react';
import { Select, Input } from 'antd';
import PhotoGroup from '../PhotoGroup';
import { withCompanySelector } from '../../hocs/withCompanySelector';
import { withSubmit } from '../../hocs/withSubmit';

const { Option } = Select;

const CollectionForm = ({ onChange }) => {
    const [trashType, setContainerType] = useState(false);
    const [containerCount, setContainerCount] = useState(false);
    const [emptyTrashImages, setEmptyTrashImages] = useState([]);
    const [fullTrashImages, setFullTrashImages] = useState([]);
    const values = useMemo(() => ({
        trashType,
        containerCount,
        emptyTrashImages,
        fullTrashImages,
        isWash: false,
    }), [trashType, containerCount, emptyTrashImages, fullTrashImages]);

    useEffect(() => {
       onChange(values);
    }, [values]);

    return (
        <div>
            <PhotoGroup
                maxPhotosCount={2}
                groupName='До відгрузки'
                onChange={setEmptyTrashImages}
            />
            <PhotoGroup
                maxPhotosCount={2}
                groupName='Після відгрузки'
                onChange={setFullTrashImages}
            />
            <div>
                <Select
                    showSearch
                    style={{ width: 200, marginTop: 15 }}
                    placeholder='Виберіть тип відходів'
                    optionFilterProp='children'
                    onChange={value => setContainerType(value)}
                >
                    <Option value='PLASTIC'>Пластик</Option>
                    <Option value='MIXED'>Змішані</Option>
                    <Option value='PAPER'>Папір</Option>
                    <Option value='ORGANIC'>Органічні</Option>
                </Select>
            </div>
            <div>
                <Input
                    placeholder='Кількість контейнерів'
                    type='number'
                    style={{ width: 200, marginTop: 20 }}
                    onChange={event => setContainerCount(+event.target.value)}
                />
            </div>
        </div>
    );
};

export default withSubmit(withCompanySelector(CollectionForm));
