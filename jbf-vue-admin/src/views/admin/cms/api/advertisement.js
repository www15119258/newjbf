/**
 * Created by lihui on 2018/4/2.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryAdvertisement (advertisement) {
  return axios.get(config.system.server_path + '/cms/advertisement/query', {params: advertisement})
}

export function addAdvertisement (advertisement) {
  return axios.post(config.system.server_path + '/cms/advertisement/save', qs.stringify(advertisement))
}

export function updateAdvertisement (advertisement) {
  return axios.put(config.system.server_path + '/cms/advertisement/update', qs.stringify(advertisement))
}

export function deleteAdvertisement (id) {
  return axios.delete(config.system.server_path + '/cms/advertisement/delete/' + id)
}
