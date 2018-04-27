/**
 * Created by lihui on 2018/4/2.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryTagcloud (tagcloud) {
  return axios.get(config.system.server_path + '/cms/tagcloud/query', {params: tagcloud})
}

export function addTagcloud (tagcloud) {
  return axios.post(config.system.server_path + '/cms/tagcloud/save', qs.stringify(tagcloud))
}

export function updateTagcloud (tagcloud) {
  return axios.put(config.system.server_path + '/cms/tagcloud/update', qs.stringify(tagcloud))
}

export function deleteTagcloud (id) {
  return axios.delete(config.system.server_path + '/cms/tagcloud/delete/' + id)
}
