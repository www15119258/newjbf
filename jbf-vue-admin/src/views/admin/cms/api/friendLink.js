/**
 * Created by lihui on 2018/4/2.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryFriendLink (friendLink) {
  return axios.get(config.system.server_path + '/cms/friendLink/query', {params: friendLink})
}

export function addFriendLink (friendLink) {
  return axios.post(config.system.server_path + '/cms/friendLink/save', qs.stringify(friendLink))
}

export function updateFriendLink (friendLink) {
  return axios.put(config.system.server_path + '/cms/friendLink/update', qs.stringify(friendLink))
}

export function deleteFriendLink (id) {
  return axios.delete(config.system.server_path + '/cms/friendLink/delete/' + id)
}
